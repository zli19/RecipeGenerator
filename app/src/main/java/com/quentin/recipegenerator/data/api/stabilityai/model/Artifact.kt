package com.quentin.openai_api_demo.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

data class Artifact(
    val base64: String,
    val finishReason: String,
    val seed: Long
)