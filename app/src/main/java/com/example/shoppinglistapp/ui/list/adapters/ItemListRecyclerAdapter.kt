package com.example.shoppinglistapp.ui.list.adapters

import android.graphics.Paint
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ItemListRowBinding
import com.example.shoppinglistapp.database.models.Item

class ItemListRecyclerAdapter(private val _listener : ItemListRecyclerClickListener) :
    ListAdapter<Item, ItemListRecyclerAdapter.ViewHolder>(ItemListDiffCallback()) {

    inner class ViewHolder(private val binding: ItemListRowBinding, private val listener : ItemListRecyclerClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            binding.checkBox.setOnClickListener { v ->
                Log.i("ItemListRecyclerView", "Clicked checkbox in view at $adapterPosition")
                listener.onCheckBoxClicked(v as CheckBox, binding.item!!)
            }
            binding.itemEditTextView.addTextChangedListener {
                    text: Editable? -> val item = binding.item!!
                    Log.i("ItemListRecyclerAdapter", "Text changed from ${item.itemName} to ${text.toString()}")
                    if (item.itemName != text.toString())
                        listener.onTextChanged(item, text.toString())
            }
        }

        fun bind(item: Item) {
            if (item.isCompleted) {
                binding.itemEditTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.itemEditTextView.paintFlags = 1
            }
            binding.item = item
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.i("ItemListRecyclerView", "Clicked view at $adapterPosition")
                listener.onCheckBoxClicked(binding.checkBox, binding.item!!)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            v?.let {
                Log.i("ItemListRecyclerView", "Clicked view at $adapterPosition")
                listener.onViewLongClicked(binding.item)
            }
            return true
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListRowBinding>(
            inflater,
            R.layout.item_list_row,
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

class ItemListDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val bool = (oldItem.itemName == newItem.itemName) and (oldItem.isCompleted == newItem.isCompleted)
        return bool
    }

}
