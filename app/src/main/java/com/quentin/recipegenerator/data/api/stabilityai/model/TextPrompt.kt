package com.quentin.openai_api_demo.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

data class TextPrompt(
    val text: String
)