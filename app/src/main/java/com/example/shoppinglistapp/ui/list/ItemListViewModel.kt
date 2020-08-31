package com.example.shoppinglistapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.database.models.*
import kotlinx.coroutines.*

class ItemListViewModel(
    private val _itemListID: Long,
    private val listDatabase: ItemListDatabaseDAO,
    private val itemDatabase: ItemDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {
    private val _listInfoLiveData: LiveData<ItemList> = listDatabase.getListInfo(_itemListID)
    val listInfoLiveData: LiveData<ItemList>
        get() = _listInfoLiveData

    val itemListId: Long
        get() = _itemListID

    private val _itemListLiveData: LiveData<List<Item>> = listDatabase.getItems(_itemListID)
    val itemListLiveData: LiveData<List<Item>>
        get() = _itemListLiveData

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("ItemListViewModel", "Initialised view model with $listDatabase.")
    }

    fun onAddItem(itemName: String) {
        uiScope.launch {
            val newItem = Item(itemName, _itemListID)
            insert(newItem)
        }
    }

    fun onDeleteItem(item: Item) {
        uiScope.launch {
            delete(item)
        }
    }

    fun onRenameList(name : String) {
        uiScope.launch {
            val list = listInfoLiveData.value
            list?.let {
                list.listName = name
                updateList(it) }
        }
    }

    fun onDeleteList() {
        uiScope.launch {
            deleteList()
        }
    }

    fun onCompleteItem(item: Item) {
        Log.i("ItemListViewModel", "Flipping completed boolean for ${item.itemName}")
        uiScope.launch {
            updateItemIsComplete(item.itemID, !item.isCompleted)
        }
    }

    fun onItemTextChanged(item: Item, text : String) {
        uiScope.launch {
            item.itemName = text
            updateItem(item)
        }
    }

    fun onDeleteCompleted() {
        uiScope.launch {
            deleteCompleted()
        }
    }

    private suspend fun insert(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.insert(item)
        }
    }

    private suspend fun updateItem(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.update(item)
        }
    }

    private suspend fun updateItemIsComplete(id : Long, isComplete : Boolean) {
        withContext(Dispatchers.IO) {
            itemDatabase.updateIsComplete(id, isComplete)
        }
    }

    private suspend fun updateList(itemList: ItemList) {
        withContext(Dispatchers.IO) {
            listDatabase.update(itemList)
        }
    }

    private suspend fun delete(item: Item) {
        withContext(Dispatchers.IO) {
            itemDatabase.delete(item)
        }
    }

    private suspend fun deleteList() {
        withContext(Dispatchers.IO) {
            listDatabase.deleteList(_itemListID)
        }
    }

    private suspend fun deleteCompleted() {
        withContext(Dispatchers.IO) {
            listDatabase.deleteCompleted(_itemListID)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}