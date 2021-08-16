package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.DialogRepositoryImpl
import com.natife.example.domain.repository.local.DialogRepository
import com.natife.example.domain.repository.local.SaveRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalRepositoryBindsModule {

    @Binds
    @Singleton
    fun localRepository(localRepository: DialogRepositoryImpl): DialogRepository

    @Binds
    @Singleton
    fun saveRepository(localRepository: DialogRepositoryImpl): SaveRepository
}