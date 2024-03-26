package com.quentin.recipegenerator.data.api.pixelsapi

import com.quentin.recipegenerator.BuildConfig
import com.quentin.recipegenerator.data.api.pixelsapi.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService{

    @Headers("Authorization: ${BuildConfig.PEXELS_API_KEY}")
    @GET("search")
    suspend fun getRecipePictures(
        @Query("query") name: String,
        @Query("per_page") number: Int = 3
    ): Response<ResponseBody>
}