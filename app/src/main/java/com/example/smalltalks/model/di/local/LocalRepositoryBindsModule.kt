package com.example.smalltalks.model.di.local

import com.example.smalltalks.model.repository.local.LocalData
import com.example.smalltalks.model.repository.local.LocalRepository
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