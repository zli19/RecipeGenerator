package com.quentin.recipegenerator.di

import com.quentin.recipegenerator.data.auth.FirebaseAuthentication
import com.quentin.recipegenerator.data.api.openaiapi.RecipeAIService
import com.quentin.recipegenerator.data.api.pixelsapi.PixelsPictureService
import com.quentin.recipegenerator.data.api.stabilityai.StabilityAIPictureService
import com.quentin.recipegenerator.data.db.FirestoreRemoteRecipeRepo
import com.quentin.recipegenerator.data.db.RoomLocalRecipeRepo
import com.quentin.recipegenerator.domain.repository.LocalRecipeRepository
import com.quentin.recipegenerator.domain.repository.RemoteRecipeRepository
import com.quentin.recipegenerator.domain.service.AIService
import com.quentin.recipegenerator.domain.service.AuthenticationService
import com.quentin.recipegenerator.domain.service.PictureService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceBindings {

    @Binds
    abstract fun bindAIService(
        recipeAIService: RecipeAIService
    ): AIService

    @Binds
    abstract fun bindAuthenticationService(
        firebaseAuthentication: FirebaseAuthentication
    ): AuthenticationService

    @Binds
    abstract fun bindLocalRecipeRepository(
        roomLocalRecipeRepo: RoomLocalRecipeRepo
    ): LocalRecipeRepository

    @Binds
    abstract fun bindRemoteRecipeRepository(
        firestoreRemoteRecipeRepo: FirestoreRemoteRecipeRepo
    ): RemoteRecipeRepository

    @Binds
    abstract fun bindPictureService(
//        stabilityAPIPictureService: StabilityAIPictureService
        pixelsPictureService: PixelsPictureService
    ): PictureService
}