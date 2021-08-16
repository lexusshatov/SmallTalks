package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.DialogRepositoryImpl
import com.example.data.repository.local.data.MessageDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalRepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(gson: Gson, dao: MessageDao) = DialogRepositoryImpl(gson, dao)
}