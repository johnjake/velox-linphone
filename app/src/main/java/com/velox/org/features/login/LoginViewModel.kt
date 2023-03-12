package com.velox.org.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velox.domain.account.AccountInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.linphone.core.Account
import org.linphone.core.AccountParams
import org.linphone.core.AuthInfo
import org.linphone.core.Core
import org.linphone.core.LogCollectionState
import org.linphone.core.TransportType
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val account: AccountInfo) : ViewModel() {

    private val mutableAccount: MutableSharedFlow<AuthState> = MutableSharedFlow()
    val accountState: SharedFlow<AuthState> = mutableAccount
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

    fun emitSuccess(account: Account) {
        viewModelScope.launch {
            mutableAccount.emit(AuthState.OnSuccess(account))
        }
    }

    fun emitFailure(error: String) {
        viewModelScope.launch {
            mutableAccount.emit(AuthState.OnFailure(error))
        }
    }

    fun emitShowLoader() {
        viewModelScope.launch {
            mutableAccount.emit(AuthState.ShowLoader)
        }
    }

    fun emitHideLoader() {
        viewModelScope.launch {
            mutableAccount.emit(AuthState.HideLoader)
        }
    }
}
