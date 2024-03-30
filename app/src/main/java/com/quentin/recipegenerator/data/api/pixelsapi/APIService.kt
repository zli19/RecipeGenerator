package com.quentin.recipegenerator.data.api.pixelsapi

import com.quentin.recipegenerator.BuildConfig
import com.quentin.recipegenerator.data.api.pixelsapi.model.PixelsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService{

    @Headers("Authorization: ${BuildConfig.PEXELS_API_KEY}")
    @GET("search")
    fun getRecipePicture(
        @Query("query") name: String,
        @Query("per_page") number: Int = 1
    ): Call<PixelsData>
}