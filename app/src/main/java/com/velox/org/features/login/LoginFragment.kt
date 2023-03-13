package com.velox.org.features.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.velox.org.bases.BaseFragment
import com.velox.org.databinding.FragmentLoginBinding
import com.velox.org.features.main.MainActivity
import com.velox.org.features.utils.gone
import com.velox.org.features.utils.navigate
import com.velox.org.features.utils.visible
import kotlinx.coroutines.flow.collectLatest
import org.linphone.core.Account
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.RegistrationState
import org.linphone.core.TransportType
import timber.log.Timber

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    private val core: Core by lazy { viewModel.createCore(null, null) }
    override fun setUpView() {
        super.setUpView()
        val act = requireActivity() as MainActivity
        act.hideNavigation()
        loaderCollect()
        authenticate()
        onKeyListener()
    }

    private fun onKeyListener() {
        binding.apply {
            email.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    textError.gone()
                    userLoginButton.visible()
                }
            }
            password.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    textError.gone()
                    userLoginButton.visible()
                }
            }
            sipAddress.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    textError.gone()
                    userLoginButton.visible()
                }
            }
        }
    }
    private fun loaderCollect() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.accountState.collectLatest { state ->
                when (state) {
                    is AuthState.ShowLoader -> handleShow()
                    is AuthState.HideLoader -> handleHide()
                    is AuthState.OnSuccess -> handleSuccess(state.account)
                    is AuthState.OnFailure -> handleFailed(state.error)
                    else -> Timber.e(" error:")
                }
            }
        }
    }

    private fun handleFailed(error: String) {
        invalidAuth()
    }

    private fun handleShow() {
        binding.progressBar.visible()
    }

    private fun handleHide() {
        binding.progressBar.gone()
    }

    private fun handleSuccess(account: Account) {
        binding.progressBar.gone()
        binding.textError.gone()
        navigateToMain()
    }

    private fun authenticate() {
        var transportType: TransportType? = null
        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                transportType = when (checkedId) {
                    radioTcp.id -> TransportType.Tcp
                    radioUdp.id -> TransportType.Udp
                    else -> TransportType.Tls
                }
            }
        }

        binding.userLoginButton.setOnClickListener {
            // onLogin(transportType)
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        navigate(LoginFragmentDirections.actionToDialerFragment())
    }

    private fun onLogin(transportType: TransportType?) {
        binding.apply {
            val userName = email.text.toString()
            val passWord = password.text.toString()
            val domain = sipAddress.text.toString()
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

    private val coreListener = object : CoreListenerStub() {
        override fun onAccountRegistrationStateChanged(core: Core, account: Account, state: RegistrationState?, message: String) {
            when (state) {
                RegistrationState.Progress -> {
                    emitShowLoader()
                }
                RegistrationState.Failed -> {
                    core.clearAllAuthInfo()
                    core.clearAccounts()
                    emitFailure(message)
                }
                RegistrationState.Ok -> {
                    emitSuccess(account)
                }
                else -> {
                    emitHideLoader()
                }
            }
        }
    }
    private fun invalidAuth() {
        binding.apply {
            textError.visible()
            userLoginButton.gone()
            passwordTimeTitle.visible()
        }
    }

    private fun emitFailure(err: String) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.emitFailure(err)
        }
    }

    private fun emitShowLoader() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.emitShowLoader()
        }
    }

    private fun emitHideLoader() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.emitHideLoader()
        }
    }

    private fun emitSuccess(account: Account) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.emitSuccess(account)
        }
    }
}
