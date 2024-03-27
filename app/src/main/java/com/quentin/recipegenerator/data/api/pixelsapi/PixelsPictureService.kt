package com.quentin.recipegenerator.data.api.pixelsapi

import com.quentin.recipegenerator.domain.model.Picture
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

class PixelsPictureService(
    private val apiService: APIService
): PictureService {
    override suspend fun fetchPictures(recipeName: String): List<Picture>? {
        return withContext(Dispatchers.IO){
            val response = async {
                apiService.getRecipePictures(recipeName)
            }
            val pictures = response.await().awaitResponse().body()?.photos?.map {
                Picture(it.src.original, it.src.landscape)
            }
            return@withContext pictures
        }
    }
}