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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainActivityToolbar)
    }
}
