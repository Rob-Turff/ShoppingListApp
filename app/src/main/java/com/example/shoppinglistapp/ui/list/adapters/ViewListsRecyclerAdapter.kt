package com.example.shoppinglistapp.ui.list.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ViewListsRowBinding
import com.example.shoppinglistapp.database.models.ItemList
import com.example.shoppinglistapp.ui.list.ViewListsFragmentDirections

class ViewListsRecyclerAdapter(private var itemLists: List<ItemList>) :
    RecyclerView.Adapter<ViewListsRecyclerAdapter.ListHolder>() {

    inner class ListHolder(private val binding: ViewListsRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(itemList: ItemList) {
            binding.itemList = itemList
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Log.i("ViewListsRecyclerView", "Clicked element in view $adapterPosition")
                v.findNavController().navigate(ViewListsFragmentDirections.actionViewListsFragmentToItemListFragment(itemLists[adapterPosition].itemListID))
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewListsRowBinding>(inflater, R.layout.view_lists_row, parent, false)
        return ListHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(itemLists[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = itemLists.size

    fun updateData(newItemLists: List<ItemList>) {
        itemLists = newItemLists
        notifyDataSetChanged()
    }
}
