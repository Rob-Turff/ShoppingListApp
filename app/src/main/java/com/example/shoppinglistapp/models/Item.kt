package com.example.shoppinglistapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(var itemName: String) : Parcelable {
    var completed : Boolean = false
}