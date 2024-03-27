package com.quentin.recipegenerator.domain.service

import com.quentin.recipegenerator.domain.model.Picture

interface PictureService {
    suspend fun fetchPictures(recipeName: String): List<Picture>?
}