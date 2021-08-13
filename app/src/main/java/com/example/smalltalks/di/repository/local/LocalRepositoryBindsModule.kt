package com.example.smalltalks.di.repository.local

import com.natife.example.domain.repository.local.LocalData
import com.example.data.repository.LocalRepository
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
    fun bindLocalRepository(localRepository: LocalRepository): LocalData
}