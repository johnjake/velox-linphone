package com.velox.org.features.login

import org.linphone.core.Account

sealed class AuthState {
    object HideLoader : AuthState()
    object ShowLoader : AuthState()
    data class OnSuccess(val account: Account) : AuthState()
    data class OnFailure(val error: String) : AuthState()
}
