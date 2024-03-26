package com.quentin.recipegenerator.data.api.pixelsapi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object PixelsAPI {
    private val BASE_URL = "https://api.pexels.com/v1/"

    val retrofitService: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }
}