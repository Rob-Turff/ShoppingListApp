package com.example.shoppinglistapp.ui.list.adapters

import android.view.View
import android.widget.CheckBox
import com.example.shoppinglistapp.database.models.Item

interface SelectItemsRecyclerClickListener {
    fun onViewClicked(view : View, item : Item)
    fun onCheckBoxClicked(item : Item)
}