package com.moto.tablet.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

//    @Provides
//    @Singleton
//    fun provideCartRepository(): CartRepository {
//        return CartRepository()
//    }
}