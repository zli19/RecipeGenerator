package com.quentin.openai_api_demo.model

data class Artifact(
    val base64: String,
    val finishReason: String,
    val seed: Long
)