package com.example.myproducts.di

import com.example.myproducts.data.network.ProductListApi
import com.example.myproducts.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideProductApi(remoteDataSource: RemoteDataSource): ProductListApi {
        return remoteDataSource.buildApi(ProductListApi::class.java)
    }
}