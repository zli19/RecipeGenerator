package com.quentin.recipegenerator.data.api.stabilityai

import com.quentin.openai_api_demo.model.ReqBody
import com.quentin.openai_api_demo.model.StabilityAIRes
import com.quentin.recipegenerator.BuildConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface StabilityAPIService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.STABILITYAI_API_KEY}",
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @POST("{engineId}/text-to-image")
    fun getRecipePicture(
        @Path("engineId") engineId: String,
        @Body reqBody: ReqBody
    ): Call<StabilityAIRes>
}