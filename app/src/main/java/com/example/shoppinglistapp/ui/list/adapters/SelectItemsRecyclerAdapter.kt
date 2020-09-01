package com.example.shoppinglistapp.ui.list.adapters

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.databinding.SelectItemRowBinding


class SelectItemsRecyclerAdapter(private val _listener : SelectItemsRecyclerClickListener) :
    ListAdapter<Item, SelectItemsRecyclerAdapter.ViewHolder>(SelectItemDiffCallback()) {

    inner class ViewHolder(private val binding: SelectItemRowBinding, private val listener : SelectItemsRecyclerClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.checkBox.setOnClickListener {
                Log.i("SelectItemsRecyclerView", "Clicked checkbox in view at $adapterPosition")
                listener.onCheckBoxClicked(binding.item!!)
            }
        }

        fun bind(item: Item) {
            if (item.isCompleted) {
                binding.itemTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.itemTextView.paintFlags = 1
            }
            binding.item = item
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.i("SelectItemsRecyclerView", "Clicked view at $adapterPosition")
                listener.onViewClicked(v, binding.item!!)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SelectItemRowBinding>(
            inflater,
            R.layout.select_item_row,
            parent,
            false
        )
        return ViewHolder(binding, _listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class SelectItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val bool = (oldItem.itemName == newItem.itemName) and (oldItem.isCompleted == newItem.isCompleted)
        if (!bool)
            newItem.isSelected = oldItem.isSelected
        return bool
    }
}