package com.example.shoppinglistapp.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.models.Item
import com.example.shoppinglistapp.models.ItemList

class ItemListViewModel(private val _itemList: ItemList) : ViewModel() {
    private val _itemListLiveData = MutableLiveData<ItemList>()
    val itemListLiveData: LiveData<ItemList>
        get() = _itemListLiveData

    init {
        Log.i("ItemListViewModel", "Initialised view model with $_itemList")
        _itemListLiveData.value = _itemList
    }

    fun onAddItem(item: String) {
        _itemList.addItem(item)
        _itemListLiveData.value = _itemList
    }

    fun onDeleteItem(item: Item) {
        _itemList.removeItem(item)
        _itemListLiveData.value = _itemList
    }

    fun onCompleteItem(postion: Int) {
        Log.i("ItemListViewModel", "Flipping completed boolean for ${_itemList.elements[postion]}")
        _itemList.elements[postion].completed = !_itemList.elements[postion].completed
        _itemList.elements.sortBy {it.completed}
        _itemListLiveData.value = _itemList
    }
}