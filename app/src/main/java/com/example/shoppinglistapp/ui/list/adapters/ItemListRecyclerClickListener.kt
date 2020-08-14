package com.example.shoppinglistapp.ui.list.adapters

import android.view.View
import android.widget.CheckBox

interface ItemListRecyclerClickListener {
    fun onViewClicked(view : View, position : Int)
    fun onCheckBoxClicked(checkBox: CheckBox, position: Int)
}