package com.velox.data.util

import org.linphone.core.Factory

class PhoneFactory {
    private fun instance(): Factory {
        return Factory.instance()
    }

    fun initiateLinPhone(domain: String) {
        instance().setLoggerDomain(domain)
        instance().enableLogcatLogs(true)
    }
}
