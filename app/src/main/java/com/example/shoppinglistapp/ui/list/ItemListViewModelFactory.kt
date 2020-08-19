package com.example.shoppinglistapp.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemList
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import java.lang.IllegalArgumentException

class ItemListViewModelFactory(
    private val itemListID: Long,
    private val listDataSource: ItemListDatabaseDAO,
    private val itemDataSource: ItemDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            return ItemListViewModel(itemListID, listDataSource, itemDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}