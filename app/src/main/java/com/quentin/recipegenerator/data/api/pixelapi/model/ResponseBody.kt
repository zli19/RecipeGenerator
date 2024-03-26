package com.quentin.recipegenerator.data.api.pixelapi.model

data class ResponseBody(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)