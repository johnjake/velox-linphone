package com.velox.org.features.login

import com.velox.org.bases.BaseFragment
import com.velox.org.databinding.FragmentLoginBinding
import com.velox.org.features.main.MainActivity
import com.velox.org.features.utils.navigate

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun setUpView() {
        super.setUpView()
        val act = requireActivity() as MainActivity
        act.hideNavigation()
        login()
    }
    private fun login() {
        binding.apply {
            userLoginButton.setOnClickListener {
                navigate(LoginFragmentDirections.actionToDialerFragment())
            }
        }
    }
}
