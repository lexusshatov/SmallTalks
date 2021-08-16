package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.DialogRepositoryImpl
import com.example.data.repository.local.PreferencesRepositoryImpl
import com.natife.example.domain.local.DialogRepository
import com.natife.example.domain.local.PreferencesRepository
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

    @Binds
    @Singleton
    fun bindDialogRepository(dialogRepositoryImpl: DialogRepositoryImpl): DialogRepository
}