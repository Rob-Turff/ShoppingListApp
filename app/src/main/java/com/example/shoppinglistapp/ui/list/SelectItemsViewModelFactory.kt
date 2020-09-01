package com.example.shoppinglistapp.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import java.lang.IllegalArgumentException

class SelectItemsViewModelFactory(
    private val itemId: Long,
    private val itemList : List<Item>,
    private val listDataSource: ItemListDatabaseDAO,
    private val itemDataSource: ItemDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectItemsViewModel::class.java)) {
            return SelectItemsViewModel(itemId, itemList, listDataSource, itemDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
