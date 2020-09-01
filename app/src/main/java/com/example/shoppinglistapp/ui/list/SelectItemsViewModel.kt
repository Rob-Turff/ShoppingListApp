package com.example.shoppinglistapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import kotlinx.coroutines.*

class SelectItemsViewModel(
    private val itemId: Long,
    val itemList: List<Item>,
    private val listDatabase: ItemListDatabaseDAO,
    private val itemDatabase: ItemDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("SelectItemsViewModel", "Initialised view model with $listDatabase.")
        for (item in itemList) {
            if (item.itemID == itemId)
                item.isSelected = true
        }
    }

    fun onSelectItem(item : Item) {
        item.isSelected = !item.isSelected
    }

    fun onDeleteSelected() {
        uiScope.launch {
            val deleteList = mutableListOf<Item>()
            for (item in itemList) {
                if (item.isSelected)
                    deleteList.add(item)
            }
            deleteSelected(deleteList)
        }
    }

    private suspend fun deleteSelected(list : List<Item>) {
        withContext(Dispatchers.IO) {
            for (item in list) {
                itemDatabase.delete(item)
            }
        }
    }
}