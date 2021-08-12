package com.example.core.di.repository.local

import com.natife.example.domain.base.repository.local.LocalData
import com.natife.example.domain.repository.LocalRepository
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