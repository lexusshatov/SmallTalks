package com.example.smalltalks.di.repository.remote

import com.example.data.repository.remote.SocketHolder
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SocketModule {

    @Provides
    @Singleton
    fun provideSocketHolder(gson: Gson) = SocketHolder(gson)
}