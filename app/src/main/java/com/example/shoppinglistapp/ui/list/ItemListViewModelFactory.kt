package com.example.shoppinglistapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.models.ItemList
import java.lang.IllegalArgumentException

class ItemListViewModelFactory (private val itemList : ItemList) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            return ItemListViewModel(itemList) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}