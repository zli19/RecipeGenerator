package com.quentin.recipegenerator.domain.service


// Represent service that provide a picture
interface PictureService {
    // Fetch picture URL based on a prompt
    suspend fun fetchPicture(prompt: String): String?
}