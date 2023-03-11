package com.velox.org.features.dialer

import com.velox.org.bases.BaseFragment
import com.velox.org.databinding.FragmentDialerBinding
import com.velox.org.features.main.MainActivity

class DialerFragment : BaseFragment<FragmentDialerBinding>(FragmentDialerBinding::inflate) {
    override fun setUpView() {
        super.setUpView()
        val act = requireActivity() as MainActivity
        act.showNavigation()
    }
}
