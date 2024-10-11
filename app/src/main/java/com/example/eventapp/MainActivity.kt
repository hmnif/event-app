package com.example.eventapp

import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eventapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_finished, R.id.searchFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu) // Correctly inflate the app bar menu
        return true
    }

    // Handle App Bar menu item clicks
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.searchFragment) // Navigate to SearchFragment
                true
            }
//            R.id.action_settings -> {
//                // Handle settings action here (for example, navigate to a settings fragment)
//                val navController = findNavController(R.id.nav_host_fragment_activity_main)
//                navController.navigate(R.id.navigation_home) // Replace with your settings fragment ID
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}