package com.example.shoppinglistapp.database.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "item_table",
    foreignKeys = [ForeignKey(entity = ItemList::class,
        parentColumns = ["list_id"], childColumns = ["item_list_id"], onDelete = CASCADE)]
)
data class Item(
    @ColumnInfo(name = "item_name")
    var itemName : String,
    @ColumnInfo(name = "item_list_id")
    var itemListID : Long
) {
    @PrimaryKey(autoGenerate = true)
    var itemID: Long = 0L

    //val tags : MutableList<String> = mutableListOf()

    @ColumnInfo(name = "is_priority")
    var isPriority : Boolean = false

    @ColumnInfo(name = "is_completed")
    var isCompleted : Boolean = false
}