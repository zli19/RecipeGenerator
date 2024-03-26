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
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeAI @Inject constructor(
    private val openAI: OpenAI
) {
    // should be private after test its lifecycle
    val chatMessages = mutableListOf<ChatMessage>(
        chatMessage {
            role = ChatRole.System
            content = """
                You are a helpful assistant that generate recipe based on provided ingredients.
                Provide your answer in JSON structure like this: $recipeSchema
            """.trimIndent()
        },
        chatMessage {
            role = ChatRole.User
            content = userMessageSample
        },
        chatMessage {
            role = ChatRole.Assistant
            content = assistantMessageSample
        }
    )
    private val modelId = ModelId("gpt-3.5-turbo-1106")

    fun addUserMessage(message: String = "Generate a new recipe with the same ingredients above."){
        val chatMessage = ChatMessage(
            role = ChatRole.User,
            content = message
        )
        chatMessages.add(chatMessage)
    }

    suspend fun generate(): Recipe? {
        Log.i("zhikun", "generating...")
        val request = chatCompletionRequest {
            responseFormat = ChatResponseFormat.JsonObject
            model = modelId
            messages = chatMessages
        }

        val response = openAI.chatCompletion(request)
        val content = response.choices.first().message.content


        return if(content == null){
            Log.i("zhikun", "Failed")
            null
        }else{
            chatMessages.add(
                ChatMessage(
                    role = ChatRole.Assistant,
                    content = content
                )
            )
            addUserMessage()
            Log.i("zhikun", content)

            Json.decodeFromString(Recipe.serializer(), content)
        }
    }
}