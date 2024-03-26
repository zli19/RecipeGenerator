package com.quentin.recipegenerator.data.api

import com.quentin.recipegenerator.data.api.pixelsapi.PixelsAPI
import com.quentin.recipegenerator.domain.model.Picture
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class PixelsPictureService(): PictureService {
    override suspend fun fetchPictures(recipeName: String): List<Picture>? {
        return withContext(Dispatchers.IO){
            val response = async {
                PixelsAPI.retrofitService.getRecipePictures(recipeName)
            }
            val pictures = response.await().body()?.photos?.map {
                Picture(it.src.small, it.src.landscape)
            }
            return@withContext pictures
        }
    }
}