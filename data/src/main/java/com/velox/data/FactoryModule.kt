package com.velox.data

import android.content.Context
import com.velox.data.util.CoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun providesCoreFactory(@ApplicationContext context: Context) = CoreFactory(context)
}
