package com.example.moneyapp.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        setupActionBarWithNavController(navController)

        //        context.getSharedPreferences("PREF_COOKIES", 0).edit().clear().apply();
        //        context.getSharedPreferences("PREF_COOKIES", 0).edit().clear().apply();
//        SharedPreferences.Editor.clear().commit()
//        SharedPreferences.Editor.clear().apply()
        val context = this;
        val settings: SharedPreferences = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}