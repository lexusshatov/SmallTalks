package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.PreferencesRepositoryImpl
import com.natife.example.domain.repository.PreferencesRepository
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
    fun bindPreferencesRepository(preferencesRepositoryImpl: PreferencesRepositoryImpl)
            : PreferencesRepository
}