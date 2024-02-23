package com.makeappssimple.abhimanyu.catfact.android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.makeappssimple.abhimanyu.catfact.android.R
import com.makeappssimple.abhimanyu.catfact.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            navGraph = navController.graph,
        )
        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration,
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(
            viewId = R.id.nav_host_fragment,
        )
        return navController.navigateUp(
            appBarConfiguration = appBarConfiguration,
        ) || super.onSupportNavigateUp()
    }
}
