package com.example.shoppinglistapp.database.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class, ItemList::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {
    abstract val itemDatabaseDAO : ItemDatabaseDAO
    abstract val itemListDatabaseDao : ItemListDatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getInstance(context: Context) : ItemsDatabase {
            synchronized(this) {
                var instance = INSTANCE

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