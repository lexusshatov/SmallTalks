package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.PreferencesRepositoryImpl
import com.natife.example.domain.repository.local.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesRepositoryBindsModule {

    @Binds
    @Singleton
    fun bindPreferencesRepository(preferencesRepository: PreferencesRepositoryImpl)
            : PreferencesRepository
}