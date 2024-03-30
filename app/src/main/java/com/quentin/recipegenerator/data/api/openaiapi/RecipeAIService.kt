package com.quentin.recipegenerator.data.api.openaiapi

import android.util.Log
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatResponseFormat
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.chatCompletionRequest
import com.aallam.openai.api.chat.chatMessage
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.quentin.recipegenerator.domain.model.Recipe
import com.quentin.recipegenerator.domain.service.AIService
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

class RecipeAIService(private val openAI: OpenAI): AIService {

    private val initialMessages = listOf(
        chatMessage {
            role = ChatRole.System
            content = """
                You are a helpful assistant that generate recipe based on provided ingredients and requirements.
                Provide your answer in JSON structure like this: $recipeSchema
            """.trimIndent()
        },
//        chatMessage {
//            role = ChatRole.User
//            content = userMessageSample
//        },
//        chatMessage {
//            role = ChatRole.Assistant
//            content = assistantMessageSample
//        }
    )

    private var chatMessages = mutableListOf<ChatMessage>()
    private val modelId = ModelId("gpt-3.5-turbo-1106")
    override suspend fun generateRecipe(ingredients: String, features: List<String>): Recipe? {
        // Initialize chatMessage list
        chatMessages.clear()
        chatMessages.addAll(initialMessages)
        // Add user message of the ingredients to OpenAI chatMessages
        val builder = StringBuilder()
            .append("Ingredients: $ingredients")
        if(features.isNotEmpty()){
            builder.append("\nFeatures: ")
            features.forEach{
                builder.append("$it, ")
            }
        }

        addUserMessage(builder.toString())
        return generate()
    }

    override suspend fun regenerateRecipe(): Recipe? {
        // Add default message for regeneration
        addUserMessage()
        return generate()
    }

    private fun addUserMessage(
        // Default message for regeneration
        message: String = "Generate a new recipe with the same ingredients above."

    ){
        val chatMessage = ChatMessage(
            role = ChatRole.User,
            content = message
        )
        chatMessages.add(chatMessage)
    }

    private suspend fun generate(): Recipe? {

        Log.i("zhikun", "generating...") //for testing

        val request = chatCompletionRequest {
            responseFormat = ChatResponseFormat.JsonObject
            model = modelId
            messages = chatMessages
        }

        val response = openAI.chatCompletion(request)
        val content = response.choices.first().message.content


        return if(content == null){
            Log.i("zhikun", "Generation Failed")
            null
        }else{

            // Add newly generated recipe result to chatMessages for potential future regeneration
            chatMessages.add(
                ChatMessage(
                    role = ChatRole.Assistant,
                    content = content
                )
            )

            Log.i("zhikun", content) //for testing

            Json.decodeFromString(Recipe.serializer(), content)
        }
    }
}