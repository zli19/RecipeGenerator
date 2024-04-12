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

class RecipeAIService(private val openAI: OpenAI): AIService {

    private val initialMessages = listOf(
        chatMessage {
            role = ChatRole.System
            content = """
                You are a helpful assistant that generate recipe based on user-provided requirements and preferences.
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
    override suspend fun generateRecipe(requirements: String, preferences: List<String>, exclusions: List<String>): Recipe? {
        // Initialize chatMessage list
        chatMessages.clear()
        chatMessages.addAll(initialMessages)
        // Add user message of the ingredients to OpenAI chatMessages
        val builder = StringBuilder()
            .append("Requirements: $requirements")
        if(preferences.isNotEmpty()){
            builder.append("\nPreferences: ")
            preferences.forEach{
                builder.append("$it, ")
            }
        }
        if(exclusions.isNotEmpty()){
            builder.append("\nExclusions: ")
            exclusions.forEach{
                builder.append("$it, ")
            }
        }

        val message = builder.toString()
        Log.i("userMessage", message)
        addUserMessage(message)
        return generate()
    }

    private fun addUserMessage( message: String ){
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
//            chatMessages.add(
//                ChatMessage(
//                    role = ChatRole.Assistant,
//                    content = content
//                )
//            )

            Log.i("zhikun", content) //for testing

            Json.decodeFromString(Recipe.serializer(), content)
        }
    }
}