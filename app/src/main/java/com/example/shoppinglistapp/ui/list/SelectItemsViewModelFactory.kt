package com.example.shoppinglistapp.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import java.lang.IllegalArgumentException

class SelectItemsViewModelFactory(
    private val itemListId: Long,
    private val itemId: Long,
    private val listDataSource: ItemListDatabaseDAO,
    private val itemDataSource: ItemDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectItemsViewModel::class.java)) {
            return SelectItemsViewModel(itemListId, itemId, listDataSource, itemDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
