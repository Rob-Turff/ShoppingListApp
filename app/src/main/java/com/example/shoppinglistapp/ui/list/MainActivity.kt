package com.example.shoppinglistapp.ui.list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toolbar : ActionBar? = null
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivityToolbar.contentInsetStartWithNavigation = 0
        setSupportActionBar(binding.mainActivityToolbar)
        toolbar = supportActionBar
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null)
            createSignInIntent()
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

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
