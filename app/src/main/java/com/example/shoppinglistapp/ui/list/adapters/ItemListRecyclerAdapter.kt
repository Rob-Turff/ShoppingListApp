package com.example.shoppinglistapp.ui.list.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ItemListRowBinding
import com.example.shoppinglistapp.databinding.ViewListsRowBinding
import com.example.shoppinglistapp.models.Item
import com.example.shoppinglistapp.models.ItemList
import com.example.shoppinglistapp.ui.list.ViewListsFragmentDirections

class ItemListRecyclerAdapter(private var itemList: ItemList) :
    RecyclerView.Adapter<ItemListRecyclerAdapter.ListHolder>() {

    inner class ListHolder(private val binding: ItemListRowBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.i("ItemListRecyclerView", "Clicked element in view $adapterPosition")
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
        return ListHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(itemList.elements[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = itemList.getSize()

    fun updateData(newItemList: ItemList) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}
