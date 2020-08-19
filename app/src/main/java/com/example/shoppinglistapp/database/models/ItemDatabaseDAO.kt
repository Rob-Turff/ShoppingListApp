package com.example.shoppinglistapp.database.models

import androidx.room.*

@Dao
interface ItemDatabaseDAO {

    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)
}