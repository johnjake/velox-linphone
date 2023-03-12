package com.velox.org.features.login

sealed class LoginState {
    object HideLoader : LoginState()
    object ShowLoader : LoginState()
}

sealed class AuthState {
    data class OnResult(val state: Boolean) : AuthState()
}
