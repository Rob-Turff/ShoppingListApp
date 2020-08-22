package com.example.shoppinglistapp.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainActivityToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_list_action_bar, menu)
        this.menu = menu
        showGroupMenuButtons(R.id.item_list_group, false)
        return super.onCreateOptionsMenu(menu)
    }

    fun showGroupMenuButtons(groupId: Int, showMenu: Boolean) {
        menu?.setGroupVisible(groupId, showMenu)
    }
}
