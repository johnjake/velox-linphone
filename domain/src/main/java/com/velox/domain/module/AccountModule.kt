package com.velox.domain.module

import com.velox.data.util.CoreFactory
import com.velox.domain.account.AccountInfo
import com.velox.domain.account.AccountInfoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountModule {
    @Singleton
    @Provides
    fun providesAccountFactory(info: CoreFactory): AccountInfo = AccountInfoImpl(info)
}
