package com.example.shoppinglistapp.ui.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shoppinglistapp.database.models.*

class ItemListViewModel(private val itemListID: Long, val listDatabase: ItemListDatabaseDAO, val itemDatabase: ItemDatabaseDAO, application: Application) : AndroidViewModel(application) {
    private val _listInfoLiveData: LiveData<ItemList> = listDatabase.getListInfo(itemListID)
    val listInfoLiveData : LiveData<ItemList>
        get() = _listInfoLiveData

    private val _itemListLiveData: LiveData<List<Item>> = listDatabase.getItems(itemListID)
    val itemListLiveData: LiveData<List<Item>>
        get() = _itemListLiveData

    init {
        Log.i("ItemListViewModel", "Initialised view model with $listDatabase.")
    }

    fun onAddItem(itemName: String) {
        itemDatabase.insert(Item(itemName, itemListID))
    }

    fun onDeleteItem(item: Item) {
        itemDatabase.delete(item)
    }

    fun onCompleteItem(item: Item) {
        Log.i("ItemListViewModel", "Flipping completed boolean for ${item.itemName}")
        item.isCompleted = !item.isCompleted
        //TODO update specific field?
        itemDatabase.update(item)
    }
}