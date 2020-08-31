package com.example.shoppinglistapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglistapp.database.models.Item
import com.example.shoppinglistapp.database.models.ItemDatabaseDAO
import com.example.shoppinglistapp.database.models.ItemList
import com.example.shoppinglistapp.database.models.ItemListDatabaseDAO

@Database(entities = [Item::class, ItemList::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {
    abstract val itemDatabaseDAO : ItemDatabaseDAO
    abstract val itemListDatabaseDao : ItemListDatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getInstance(context: Context) : ItemsDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsDatabase::class.java,
                        "items_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    //TODO setup proper migrations
                }
                return instance
            }
        }
    }
}