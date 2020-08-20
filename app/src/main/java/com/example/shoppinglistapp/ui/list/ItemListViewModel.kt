package com.example.shoppinglistapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.database.models.*
import kotlinx.coroutines.*

class ItemListViewModel(
    private val itemListID: Long,
    listDatabase: ItemListDatabaseDAO,
    private val itemDatabase: ItemDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {
    private val _listInfoLiveData: LiveData<ItemList> = listDatabase.getListInfo(itemListID)
    val listInfoLiveData: LiveData<ItemList>
        get() = _listInfoLiveData

    private val _itemListLiveData: LiveData<List<Item>> = listDatabase.getItems(itemListID)
    val itemListLiveData: LiveData<List<Item>>
        get() = _itemListLiveData

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("ItemListViewModel", "Initialised view model with $listDatabase.")
    }

    fun onAddItem(itemName: String) {
        uiScope.launch {
            val newItem = Item(itemName, itemListID)
            insert(newItem)
        }
    }

    fun onDeleteItem(item: Item) {
        uiScope.launch {
            delete(item)
        }
    }

    fun onCompleteItem(item: Item) {
        Log.i("ItemListViewModel", "Flipping completed boolean for ${item.itemName}")
        uiScope.launch {
            item.isCompleted = !item.isCompleted
            update(item)
        }
    }

    private suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.insert(item)
        }
    }

    private suspend fun update(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.update(item)
        }
    }

    private suspend fun delete(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.delete(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}