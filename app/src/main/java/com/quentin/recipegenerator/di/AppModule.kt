package com.quentin.recipegenerator.di

import android.content.Context
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.logging.Logger
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.quentin.recipegenerator.BuildConfig
import com.quentin.recipegenerator.data.api.PixelsPictureService
import com.quentin.recipegenerator.data.db.RoomRecipeRepo
import com.quentin.recipegenerator.domain.repository.RecipeRepository
import com.quentin.recipegenerator.domain.service.PictureService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideRecipeRepository(@ApplicationContext context: Context): RecipeRepository{
        return RoomRecipeRepo(context)
    }

    @Provides
    @Singleton
    fun providePictureService(): PictureService{
        return PixelsPictureService()
    }

}