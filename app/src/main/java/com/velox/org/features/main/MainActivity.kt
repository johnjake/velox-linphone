package com.velox.org.features.main

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.velox.org.R
import com.velox.org.bases.BaseActivity
import com.velox.org.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var bottomNavView: BottomNavigationView
    override fun setUpView() {
        super.setUpView()
        setupNavigationMenu()
    }
    fun hideNavigation() {
        binding.apply {
            bottomNavigation.visibility = View.GONE
        }
    }

    fun showNavigation() {
        binding.apply {
            bottomNavigation.visibility = View.VISIBLE
        }
    }

    private fun setupNavigationMenu() {
        bottomNavView = binding.bottomNavigation.apply {
            itemIconTintList = null
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.feeds_stack -> navController.navigate(R.id.dialerFragment)
                R.id.history_stack -> navController.navigate(R.id.historyFragment)
                R.id.inbox_stack -> navController.navigate(R.id.inboxFragment)
                R.id.profile_stack -> navController.navigate(R.id.profileFragment)
            }
            true
        }
    }
}
