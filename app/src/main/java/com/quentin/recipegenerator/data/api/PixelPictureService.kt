package com.quentin.recipegenerator.data.api

import com.quentin.recipegenerator.domain.service.PictureService

class PixelPictureService(): PictureService {
    override suspend fun fetchPictures(): List<String> {
        TODO("Not yet implemented")
    }
}