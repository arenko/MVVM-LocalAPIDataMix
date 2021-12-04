package com.arenko.rijksapp.di

import com.arenko.rijksapp.domain.repository.RijksRepoInterface
import com.arenko.rijksapp.domain.repository.RijksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RijksRepoModule {

    @Binds
    abstract fun bindRepoModule(
        rijksRepository: RijksRepository
    ): RijksRepoInterface
}