package com.sena.crud.di

import com.sena.crud.data.repository.ProductRepositoryImpl
import com.sena.crud.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}