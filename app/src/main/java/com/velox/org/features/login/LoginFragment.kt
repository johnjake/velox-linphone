package com.velox.org.features.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.velox.org.bases.BaseFragment
import com.velox.org.databinding.FragmentLoginBinding
import com.velox.org.features.main.MainActivity
import com.velox.org.features.utils.navigate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.linphone.core.Account
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.RegistrationState
import org.linphone.core.TransportType

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    val mutableLoader: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val counterState: SharedFlow<Boolean> = mutableLoader

    private val viewModel: LoginViewModel by viewModels()
    private val core: Core by lazy { viewModel.createCore(null, null) }
    override fun setUpView() {
        super.setUpView()
        val act = requireActivity() as MainActivity
        act.hideNavigation()
        loaderCollect()
        login()
    }
    private fun loaderCollect() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mutableLoader.collectLatest { state ->
                when(state) {
                    true -> binding.progressBar.visibility = View.VISIBLE
                    false -> binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun login() {
        var transportType: TransportType? = null
        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                transportType = when (checkedId) {
                    radioTcp.id -> TransportType.Tcp
                    radioUdp.id -> TransportType.Udp
                    else -> TransportType.Tls
                }
            }
            /*userLoginButton.setOnClickListener {
                navigate(LoginFragmentDirections.actionToDialerFragment())
            }*/
            userLoginButton.setOnClickListener {
                val userName = email.text.toString()
                val passWord = password.text.toString()
                val domain = "sip.linphone.org"
                val authDetails = viewModel.authInfo(
                    username = userName,
                    userId = null,
                    password = passWord,
                    ha1 = null,
                    realm = null,
                    domain = domain,
                    algorithm = null,
                )
                val param = viewModel.accountParams(
                    username = userName,
                    domain = domain,
                    core = core,
                    transportType = transportType ?: TransportType.Tls,
                )
                val initCore = viewModel.initializedCore(
                    core = core,
                    authInfo = authDetails,
                    params = param,
                )
                initCore.addListener(coreListener)
                initCore.start()
            }
        }
    }

    private val coreListener = object : CoreListenerStub() {
        override fun onAccountRegistrationStateChanged(core: Core, account: Account, state: RegistrationState?, message: String) {
            when (state) {
                RegistrationState.Progress -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        mutableLoader.emit(true)
                    }
                }
                RegistrationState.Failed -> {
                    core.clearAllAuthInfo()
                    core.clearAccounts()
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        mutableLoader.emit(false)
                    }
                }
                RegistrationState.Ok -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        mutableLoader.emit(false)
                    }
                    navigate(LoginFragmentDirections.actionToDialerFragment())
                }
                else -> {
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        mutableLoader.emit(false)
                    }
                }
            }
        }
    }
}
