package com.quentin.recipegenerator.data.api.pixelsapi

import com.quentin.recipegenerator.data.api.pixelsapi.model.PixelsData
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class PixelsPictureService(
    private val pixelsApiService: PixelsAPIService
): PictureService {
    override suspend fun fetchPicture(prompt: String): String? {
        return withContext(Dispatchers.IO) {
            val response = async {
                pixelsApiService.getRecipePicture(prompt)
            }
            return@withContext response.await().awaitResponse<PixelsData>()
                .body()?.photos?.get(0)?.src?.landscape
        }
    }
}