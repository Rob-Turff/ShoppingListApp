package com.example.shoppinglistapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity (tableName = "item_list_table")
data class ItemList(
    @ColumnInfo(name = "list_name")
    var listName : String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    var itemListID : Long = 0L
}