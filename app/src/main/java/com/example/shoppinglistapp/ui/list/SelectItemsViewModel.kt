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
    private val itemListId: Long,
    private val itemId: Long,
    private val listDatabase: ItemListDatabaseDAO,
    private val itemDatabase: ItemDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {
    private val _itemListLiveData: LiveData<List<Item>> = listDatabase.getItems(itemListId)
    val itemListLiveData: LiveData<List<Item>>
        get() = _itemListLiveData

    var pairList = mutableListOf<Pair<Item, Boolean>>()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("SelectItemsViewModel", "Initialised view model with $listDatabase.")
    }

    fun updatedData() {
        val newPairList = mutableListOf<Pair<Item, Boolean>>()
        for (item in itemListLiveData.value!!) {
            newPairList.add(Pair(item, false))
        }
        pairList = newPairList
    }

    fun onDeleteSelected() {
        uiScope.launch {
            val deleteList = mutableListOf<Item>()
            for (pair in pairList) {
                if (pair.second)
                    deleteList.add(pair.first)
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