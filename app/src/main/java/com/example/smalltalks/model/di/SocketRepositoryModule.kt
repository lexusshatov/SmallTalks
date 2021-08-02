package com.example.smalltalks.model.di

import com.example.smalltalks.model.repository.SocketRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object SocketRepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSocketRepository(gson: Gson) = SocketRepository(gson)
}