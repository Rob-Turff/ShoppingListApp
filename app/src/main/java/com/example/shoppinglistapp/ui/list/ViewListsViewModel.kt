package com.example.shoppinglistapp.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.database.models.ItemList

class ViewListsViewModel : ViewModel() {
    private val lists = mutableListOf<ItemList>()
    private val _listsLiveData = MutableLiveData<List<ItemList>>()
    val listsLiveData : LiveData<List<ItemList>>
        get() = _listsLiveData

    init {
        Log.i("ViewListsViewModel", "ViewListsViewModel created!")
        _listsLiveData.value = lists
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewListsViewModel", "ViewListsViewModel destroyed!")
    }

    fun onCreateList(listName: String) {
        Log.i("ViewListsViewModel", "Created new list called $listName")
        lists.add(ItemList(listName))
        _listsLiveData.value = lists
    }

    fun removeList(list: ItemList) {
        lists.remove(list)
        _listsLiveData.value = lists
    }
}