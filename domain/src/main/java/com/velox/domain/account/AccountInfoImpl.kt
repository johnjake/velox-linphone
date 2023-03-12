package com.velox.domain.account

import com.velox.data.util.CoreFactory
import org.linphone.core.AccountParams
import org.linphone.core.AuthInfo
import org.linphone.core.Core
import org.linphone.core.LogCollectionState
import org.linphone.core.TransportType
import javax.inject.Inject

class AccountInfoImpl @Inject constructor(private val info: CoreFactory) : AccountInfo {
    override fun createCore(configPath: String?, factoryConfig: String?): Core {
        return info.createCore(
            configPath = configPath,
            factoryConfig = factoryConfig,
        )
    }

    override fun clearCache(path: String, logState: LogCollectionState, executeDelete: () -> Unit) {
        info.clearCache(path = path, logState = logState, executeDelete = executeDelete)
    }

    override fun accountInfo(
        username: String,
        userId: String?,
        password: String?,
        ha1: String?,
        realm: String?,
        domain: String?,
        algorithm: String?,
    ): AuthInfo {
        return info.accountInfo(
            username = username,
            userId = userId,
            password = password,
            ha1 = ha1,
            realm = realm,
            domain = domain,
            algorithm = algorithm,
        )
    }

    override fun accountParams(
        username: String,
        domain: String,
        core: Core,
        transportType: TransportType,
    ): AccountParams {
        return info.accountParams(
            username = username,
            domain = domain,
            core = core,
            transportType = transportType,
        )
    }

    override fun initializedCore(
        core: Core,
        authInfo: AuthInfo,
        params: AccountParams,
    ): Core {
        return info.initializedCore(
            core = core,
            authInfo = authInfo,
            params = params,
        )
    }
}
