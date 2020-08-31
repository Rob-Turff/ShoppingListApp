package com.example.shoppinglistapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoppinglistapp.database.ItemsDatabase
import com.example.shoppinglistapp.database.models.*
import com.jraska.livedata.test
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ItemDatabaseTest {
    @get:Rule val testRule = InstantTaskExecutorRule()

    private lateinit var itemDatabaseDAO: ItemDatabaseDAO
    private lateinit var itemListDatabaseDAO: ItemListDatabaseDAO
    private lateinit var db: ItemsDatabase

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ItemsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDatabaseDAO = db.itemDatabaseDAO
        itemListDatabaseDAO = db.itemListDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetList() {
        val list = ItemList("Potato")
        val listID = itemListDatabaseDAO.insert(list)
        val item = Item("Marris Piper", listID)
        itemDatabaseDAO.insert(item)
        val databaseList = itemListDatabaseDAO.getListInfo(listID)
        databaseList.test().awaitValue()
        assertEquals(databaseList.value!!.listName, list.listName)
        val databaseItems = itemListDatabaseDAO.getItems(listID)
        databaseItems.test().awaitValue()
        assertEquals(databaseItems.value!!.first().itemName, item.itemName)
    }

}
