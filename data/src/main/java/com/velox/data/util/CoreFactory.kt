package com.velox.data.util

import android.content.Context
import org.linphone.core.AccountParams
import org.linphone.core.AuthInfo
import org.linphone.core.Core
import org.linphone.core.Factory
import org.linphone.core.LogCollectionState
import org.linphone.core.TransportType
import javax.inject.Inject

class CoreFactory @Inject constructor(private val context: Context) {
    private val factory = Factory.instance()
    fun clearCache(path: String, logState: LogCollectionState, executeDelete: () -> Unit) {
        factory.setLogCollectionPath(path)
        factory.enableLogCollection(logState)
        executeDelete()
    }
    fun createCore(configPath: String?, factoryConfig: String?): Core {
        val core: Core by lazy {
            factory.createCore(configPath, factoryConfig, context)
        }
        return core
    }

    fun accountInfo(
        username: String,
        userId: String?,
        password: String?,
        ha1: String?,
        realm: String?,
        domain: String?,
        algorithm: String?,
    ): AuthInfo {
        return factory.createAuthInfo(
            username,
            userId,
            password,
            ha1,
            realm,
            domain,
            algorithm,
        )
    }

    fun accountParams(
        username: String,
        domain: String,
        core: Core,
        transportType: TransportType,
    ): AccountParams {
        val accountParams = core.createAccountParams()
        val identity = accountIdentity(username = username, domain = domain)
        val address = accountAddress(domain)
        accountParams.identityAddress = identity
        address?.transport = transportType
        accountParams.serverAddress = address
        accountParams.isRegisterEnabled = true
        // We need a conference factory URI set on the Account to be able to create chat rooms with flexisip backend
        // accountParams.conferenceFactoryUri = "sip:conference-factory@sip.linphone.org"
        return accountParams
    }

    fun initializedCore(
        core: Core,
        authInfo: AuthInfo,
        params: AccountParams,

    ): Core {
        core.addAuthInfo(authInfo)
        val account = core.createAccount(params)
        core.addAccount(account)
        // We also need a LIME X3DH server URL configured for end to end encryption
        core.limeX3DhServerUrl = "https://lime.linphone.org/lime-server/lime-server.php"
        core.defaultAccount = account
        return core
    }

    private fun accountIdentity(
        username: String,
        domain: String,
    ) = factory.createAddress("sip:$username@$domain")

    private fun accountAddress(domain: String) = factory.createAddress("sip:$domain")
}
