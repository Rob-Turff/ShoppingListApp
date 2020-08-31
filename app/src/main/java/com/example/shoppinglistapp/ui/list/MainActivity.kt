package com.example.shoppinglistapp.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toolbar : ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivityToolbar.contentInsetStartWithNavigation = 0
        setSupportActionBar(binding.mainActivityToolbar)
        toolbar = supportActionBar
    }

    fun showHomeButton(doShow : Boolean) {
        toolbar?.setDisplayHomeAsUpEnabled(doShow)
        toolbar?.setDisplayShowHomeEnabled(doShow)
    }

    fun changeHomeIcon(showCross : Boolean) {
        val toolbar : Toolbar = binding.mainActivityToolbar
        if (showCross)
            toolbar.setNavigationIcon(R.drawable.close_icon)
        else
            toolbar.setNavigationIcon(R.drawable.back_arrow)
    }
}
