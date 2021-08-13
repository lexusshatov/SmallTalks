package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.RemoteRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(gson: Gson) = RemoteRepository(gson)
}