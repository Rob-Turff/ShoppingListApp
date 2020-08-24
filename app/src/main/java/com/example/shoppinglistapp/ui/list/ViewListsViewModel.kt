package com.example.shoppinglistapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.database.models.ItemList
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO
import kotlinx.coroutines.*

class ViewListsViewModel(
    private val database: ItemListDatabaseDAO,
    application: Application
) : AndroidViewModel(application) {
    private val _listsLiveData = database.getAllLists()
    val listsLiveData: LiveData<List<ItemList>>
        get() = _listsLiveData

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        Log.i("ViewListsViewModel", "ViewListsViewModel created!")
    }

    fun onCreateList(listName: String) {
        Log.i("ViewListsViewModel", "Created new list called $listName")
        uiScope.launch {
            val newList = ItemList(listName)
            insert(newList)
        }
    }

    fun onDeleteList(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }

    private suspend fun insert(list: ItemList) {
        withContext(Dispatchers.IO) {
            database.insert(list)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.deleteList(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}