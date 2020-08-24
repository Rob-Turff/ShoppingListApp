package com.example.shoppinglistapp.database.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ItemListDatabaseDAO {
    @Insert
    fun insert(itemList: ItemList) : Long

    @Update
    fun update(itemList: ItemList)

    @Query("SELECT * FROM item_table WHERE item_list_id = :id")
    fun getItems(id : Long): LiveData<List<Item>>

    @Query("SELECT * FROM item_list_table WHERE list_id = :id")
    fun getListInfo(id : Long) : LiveData<ItemList>

    @Query("SELECT * FROM item_list_table")
    fun getAllLists() : LiveData<List<ItemList>>

    @Query("DELETE FROM item_table WHERE item_list_id = :id AND is_completed = 1")
    fun deleteCompleted(id : Long)

    @Delete
    fun deleteList(itemList: ItemList)
}