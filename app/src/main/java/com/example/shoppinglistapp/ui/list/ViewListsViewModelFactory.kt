package com.example.shoppinglistapp.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import java.lang.IllegalArgumentException

class ViewListsViewModelFactory(
    private val listDataSource: ItemListDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewListsViewModel::class.java)) {
            return ViewListsViewModel(listDataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}