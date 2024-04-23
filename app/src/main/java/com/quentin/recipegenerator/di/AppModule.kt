package com.quentin.recipegenerator.di

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.logging.Logger
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.quentin.recipegenerator.BuildConfig
import com.quentin.recipegenerator.data.api.pixelsapi.PixelsAPIService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpenAI(): OpenAI{
        val token = BuildConfig.OPENAI_API_KEY

        val config = OpenAIConfig(
            token = token,
            logging = LoggingConfig(
                logLevel = LogLevel.None,
                logger = Logger.Empty
            ),
            timeout = Timeout(socket = 60.seconds)
        )

        return OpenAI(config)
    }

    @Provides
    @Singleton
    fun providePixelsAPIService(): PixelsAPIService {
        val BASE_URL = "https://api.pexels.com/v1/"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PixelsAPIService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideStabilityAPIService(): StabilityAPIService {
//        val STABILITY_BASE_URL = "https://api.stability.ai/v1/generation/"
//
//        return Retrofit.Builder()
//            .baseUrl(STABILITY_BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//            .create(StabilityAPIService::class.java)
//    }

}