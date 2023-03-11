package com.velox.org.features.main

import android.view.View
import com.velox.org.bases.BaseActivity
import com.velox.org.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun setUpView() {
        super.setUpView()
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
}
