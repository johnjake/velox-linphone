package com.velox.org.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velox.domain.account.AccountInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.linphone.core.Account
import org.linphone.core.AccountParams
import org.linphone.core.AuthInfo
import org.linphone.core.Core
import org.linphone.core.CoreListenerStub
import org.linphone.core.LogCollectionState
import org.linphone.core.RegistrationState
import org.linphone.core.TransportType
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(private val account: AccountInfo) : ViewModel() {

    /** call back account listener **/

    private val mutableAuth: MutableSharedFlow<LoginState> = MutableSharedFlow(replay = 1)
    val stateFlow: SharedFlow<LoginState> = mutableAuth

    fun createCore(configPath: String?, factoryConfig: String?): Core {
        return account.createCore(configPath = configPath, factoryConfig = factoryConfig)
    }

    fun clearCache(
        path: String,
        logState: LogCollectionState,
        executeDelete: () -> Unit,
    ) {
        account.clearCache(path = path, logState = logState, executeDelete = executeDelete)
    }

    fun authInfo(
        username: String,
        userId: String?,
        password: String?,
        ha1: String?,
        realm: String?,
        domain: String?,
        algorithm: String?,
    ): AuthInfo {
        return account.accountInfo(
            username = username,
            userId = userId,
            password = password,
            ha1 = ha1,
            realm = realm,
            domain = domain,
            algorithm = algorithm,
        )
    }

    fun accountParams(
        username: String,
        domain: String,
        core: Core,
        transportType: TransportType,
    ): AccountParams {
        return account.accountParams(
            username = username,
            domain = domain,
            core = core,
            transportType = transportType,
        )
    }

    fun initializedCore(
        core: Core,
        authInfo: AuthInfo,
        params: AccountParams,
    ): Core {
        return account.initializedCore(
            core = core,
            authInfo = authInfo,
            params = params,
        )
    }

    suspend fun coreListener(): Boolean = suspendCoroutine { continuation ->
        object : CoreListenerStub() {
            override fun onAccountRegistrationStateChanged(
                core: Core,
                account: Account,
                state: RegistrationState?,
                message: String,
            ) {
                when (state) {
                    RegistrationState.Progress -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                mutableAuth.emit(LoginState.ShowLoader)
                            }
                        }
                    }
                    RegistrationState.Ok -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                mutableAuth.emit(LoginState.HideLoader)
                            }
                        }
                        continuation.resume(true)
                    }
                    else -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                mutableAuth.emit(LoginState.HideLoader)
                            }
                        }
                        continuation.resumeWithException(Throwable(message))
                    }
                }
            }
        }
    }
}
