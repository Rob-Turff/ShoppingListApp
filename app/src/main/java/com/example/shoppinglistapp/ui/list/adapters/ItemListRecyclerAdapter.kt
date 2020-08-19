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
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ItemListRowBinding
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemList

class ItemListRecyclerAdapter(private var itemList: List<Item>, private val _listener : ItemListRecyclerClickListener) :
    RecyclerView.Adapter<ItemListRecyclerAdapter.ListHolder>() {

    inner class ListHolder(private val binding: ItemListRowBinding, private val listener : ItemListRecyclerClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.checkBox.setOnClickListener { v ->
                Log.i("ItemListRecyclerView", "Clicked checkbox in view at $adapterPosition")
                listener.onCheckBoxClicked(v as CheckBox, binding.item!!)
            }
            binding.itemEditTextView.addTextChangedListener { text: Editable? -> binding.item?.itemName = text.toString() }
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
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListRowBinding>(
            inflater,
            R.layout.item_list_row,
            parent,
            false
        )
        return ListHolder(binding, _listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(itemList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = itemList.size

    fun updateData(newItemList: List<Item>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}
