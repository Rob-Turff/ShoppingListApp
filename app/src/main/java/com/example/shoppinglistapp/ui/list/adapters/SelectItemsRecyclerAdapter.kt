package com.example.shoppinglistapp.ui.list.adapters

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.databinding.SelectItemRowBinding


class SelectItemsRecyclerAdapter(private var itemList: List<Pair<Item, Boolean>>, private val _listener : SelectItemsRecyclerClickListener) :
    RecyclerView.Adapter<SelectItemsRecyclerAdapter.ListHolder>() {
    inner class ListHolder(private val binding: SelectItemRowBinding, private val listener : SelectItemsRecyclerClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.checkBox.setOnClickListener { v ->
                Log.i("SelectItemsRecyclerView", "Clicked checkbox in view at $adapterPosition")
                listener.onCheckBoxClicked(v as CheckBox, adapterPosition)
            }
        }

        fun bind(itemPair: Pair<Item, Boolean>) {
            if (itemPair.first.isCompleted) {
                binding.itemEditTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.itemEditTextView.paintFlags = 1
            }
            binding.item = itemPair.first
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.i("SelectItemsRecyclerView", "Clicked view at $adapterPosition")
                listener.onViewClicked(v, adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SelectItemRowBinding>(
            inflater,
            R.layout.select_item_row,
            parent,
            false
        )
        return ListHolder(binding, _listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun updateData(newItemList: List<Pair<Item, Boolean>>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = itemList.size
}