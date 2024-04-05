package com.quentin.recipegenerator.di

import android.content.Context
import com.quentin.recipegenerator.data.api.pixelsapi.PixelsAPIService
import com.quentin.recipegenerator.data.api.pixelsapi.PixelsPictureService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAIPictureService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAPIService
import com.quentin.recipegenerator.domain.service.PictureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private const val BASE_URL = "https://api.pexels.com/v1/"
//
//    @Provides
//    @Singleton
//    fun providePixelsAPIService(): PixelsAPIService {
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//            .create(PixelsAPIService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun providePictureService(pixelsApiService: PixelsAPIService): PictureService {
//        return PixelsPictureService(pixelsApiService)
//    }

    private const val STABILITY_BASE_URL = "https://api.stability.ai/v1/generation/"

    @Provides
    @Singleton
    fun provideStabilityAPIService(): StabilityAPIService {

        return Retrofit.Builder()
            .baseUrl(STABILITY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StabilityAPIService::class.java)
    }

    @Provides
    @Singleton
    fun providePictureService(
        stabilityAPIService: StabilityAPIService,
        @ApplicationContext appContext: Context
    ): PictureService {
        return StabilityAIPictureService(stabilityAPIService, appContext)
    }

}