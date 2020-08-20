package com.example.shoppinglistapp.ui.list.adapters

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.example.shoppinglistapp.database.models.Item

interface ItemListRecyclerClickListener {
    fun onViewClicked(view : View, position : Int)
    fun onCheckBoxClicked(checkBox: CheckBox, item: Item)
    fun onTextChanged(item: Item, text : String)
}