package com.quentin.recipegenerator.domain.service

interface PictureService {
    suspend fun fetchPictures(recipeName: String): List<Picture>?
}