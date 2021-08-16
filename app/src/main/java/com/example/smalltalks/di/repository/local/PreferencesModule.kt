package com.example.smalltalks.di.repository.local

import android.content.Context
import com.example.data.repository.local.PreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context) =
        PreferencesRepositoryImpl(context)
}