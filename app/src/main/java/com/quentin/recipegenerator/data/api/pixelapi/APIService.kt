package com.quentin.recipegenerator.data.api.pixelapi

import com.quentin.recipegenerator.data.api.pixelapi.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService{
    @GET
    suspend fun getRecipePictures(
        @Query("search") name: String,
        @Query("per_page") number: Int = 3
    ): Response<ResponseBody>
}