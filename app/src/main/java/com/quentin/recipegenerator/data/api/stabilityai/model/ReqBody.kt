package com.quentin.openai_api_demo.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

data class ReqBody(
    val cfg_scale: Int = 7,
    val height: Int = 512,
    val samples: Int = 1,
    val steps: Int = 30,
    val text_prompts: List<TextPrompt>,
    val width: Int = 512
)