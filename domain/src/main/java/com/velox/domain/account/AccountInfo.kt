package com.velox.domain.account

import org.linphone.core.AccountParams
import org.linphone.core.AuthInfo
import org.linphone.core.Core
import org.linphone.core.LogCollectionState
import org.linphone.core.TransportType

interface AccountInfo {
    fun createCore(configPath: String?, factoryConfig: String?): Core
    fun clearCache(
        path: String,
        logState: LogCollectionState,
        executeDelete: () -> Unit,
    )
    fun accountInfo(
        username: String,
        userId: String?,
        password: String?,
        ha1: String?,
        realm: String?,
        domain: String?,
        algorithm: String?,
    ): AuthInfo

    fun accountParams(
        username: String,
        domain: String,
        core: Core,
        transportType: TransportType,
    ): AccountParams

    fun initializedCore(
        core: Core,
        authInfo: AuthInfo,
        params: AccountParams,

    ): Core
}
