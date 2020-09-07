package com.example.shoppinglistapp.database.models

import androidx.room.*
import java.lang.StringBuilder

@Dao
interface ItemDatabaseDAO {

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)

    @Query("UPDATE ITEM_TABLE SET item_name = :name WHERE itemID = :id")
    fun updateItemName(id : Long, name : String)

    @Query("UPDATE ITEM_TABLE SET is_completed = :isComplete WHERE itemID = :id")
    fun updateIsComplete(id : Long, isComplete : Boolean)
}