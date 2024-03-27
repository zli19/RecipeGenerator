package com.quentin.recipegenerator.di

import com.quentin.recipegenerator.data.api.pixelsapi.APIService
import com.quentin.recipegenerator.data.api.pixelsapi.PixelsPictureService
import com.quentin.recipegenerator.domain.service.PictureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.pexels.com/v1/"

    @Provides
    @Singleton
    fun provideAPIService(): APIService {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun providePictureService(apiService: APIService): PictureService {
        return PixelsPictureService(apiService)
    }

}