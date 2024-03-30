package com.quentin.recipegenerator.domain.service

interface PictureService {
    suspend fun fetchPicture(prompt: String): String?
}