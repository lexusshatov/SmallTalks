package com.example.domain.di.local

import com.example.domain.repository.LocalRepository
import com.example.domain.repository.base.repository.LocalData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface LocalRepositoryBindsModule {

    @Binds
    fun bindLocalRepository(localRepository: LocalRepository): LocalData
}