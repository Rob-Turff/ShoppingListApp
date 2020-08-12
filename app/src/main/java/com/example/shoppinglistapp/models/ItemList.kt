package com.example.shoppinglistapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemList (var ListName: String) : Parcelable {
    val elements = mutableListOf<Item>()

    override fun toString(): String {
        return "$ListName"
    }

    fun addItem(itemName: String) {
        elements.add(Item(itemName))
    }

    fun removeItem(item: Item) {
        elements.remove(item)
    }
}