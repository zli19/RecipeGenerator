package com.quentin.recipegenerator.di

import android.content.Context
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.logging.Logger
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.quentin.recipegenerator.BuildConfig
import com.quentin.recipegenerator.auth.FirebaseAuthentication
import com.quentin.recipegenerator.data.api.openaiapi.RecipeAIService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAIPictureService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAPIService
import com.quentin.recipegenerator.data.db.FirestoreRemoteRecipeRepo
import com.quentin.recipegenerator.data.db.RoomLocalRecipeRepo
import com.quentin.recipegenerator.domain.repository.LocalRecipeRepository
import com.quentin.recipegenerator.domain.repository.RemoteRecipeRepository
import com.quentin.recipegenerator.domain.service.AIService
import com.quentin.recipegenerator.domain.service.AuthenticationService
import com.quentin.recipegenerator.domain.service.PictureService
import com.quentin.recipegenerator.domain.usecase.DataPersistence
import com.quentin.recipegenerator.domain.usecase.GenerateRecipe
import com.quentin.recipegenerator.domain.usecase.UseCases
import com.quentin.recipegenerator.domain.usecase.UserAuthentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAIService(openAI: OpenAI): AIService{
        return RecipeAIService(openAI)
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(): AuthenticationService {
        return FirebaseAuthentication()
    }

    @Provides
    @Singleton
    fun provideLocalRecipeRepository(@ApplicationContext context: Context): LocalRecipeRepository {
        return RoomLocalRecipeRepo(context)
    }

    @Provides
    @Singleton
    fun provideRemoteRecipeRepository(): RemoteRecipeRepository {
        return FirestoreRemoteRecipeRepo()
    }

    @Provides
    @Singleton
    fun provideUseCases(
        localRecipeRepository: LocalRecipeRepository,
        remoteRecipeRepository: RemoteRecipeRepository,
        aiService: AIService,
        pictureService: PictureService,
        authenticationService: AuthenticationService
    ): UseCases {
        return UseCases(
            generateRecipe = GenerateRecipe(aiService, pictureService),
            dataPersistence = DataPersistence(localRecipeRepository, remoteRecipeRepository),
            userAuthentication = UserAuthentication(authenticationService)
        )
    }
}