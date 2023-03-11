package com.velox.org.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.velox.org.hilt.HiltActivityEntry

abstract class BaseActivity<T : ViewBinding>(
    private val setUpViewBinding: (LayoutInflater) -> T,
) : HiltActivityEntry() {
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setUpViewBinding(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setUpObserver()
        setUpView()
    }
    open fun setUpObserver() {}
    open fun setUpView() {}
}
