package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.LocalRepositoryImpl
import com.example.data.repository.local.PreferencesRepositoryImpl
import com.natife.example.domain.repository.local.LocalRepository
import com.natife.example.domain.repository.local.PreferencesRepository
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
    fun localRepository(localRepository: LocalRepositoryImpl): LocalRepository

    @Binds
    @Singleton
    fun saveRepository(localRepository: LocalRepositoryImpl): SaveRepository

    @Binds
    @Singleton
    fun preferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository
}