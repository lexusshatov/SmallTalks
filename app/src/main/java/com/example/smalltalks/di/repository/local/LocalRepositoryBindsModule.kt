package com.example.smalltalks.di.repository.local

import com.example.data.repository.local.LocalRepositoryImpl
import com.example.data.repository.local.PreferencesRepositoryImpl
import com.natife.example.domain.repository.local.LocalRepository
import com.natife.example.domain.repository.local.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
interface LocalRepositoryBindsModule {

    @Binds
    @ActivityRetainedScoped
    fun localRepository(localRepository: LocalRepositoryImpl): LocalRepository

    @Binds
    @ActivityRetainedScoped
    fun preferencesRepository(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository
}