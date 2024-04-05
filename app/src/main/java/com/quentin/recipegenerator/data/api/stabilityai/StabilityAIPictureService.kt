package com.quentin.recipegenerator.data.api.stabilityai

import android.content.Context
import android.util.Base64
import android.util.Log
import com.quentin.openai_api_demo.model.ReqBody
import com.quentin.openai_api_demo.model.TextPrompt
import com.quentin.recipegenerator.domain.service.PictureService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StabilityAIPictureService(
    private val stabilityAPIService: StabilityAPIService,
    private val context: Context
): PictureService {
    private val engineId = "stable-diffusion-v1-6"
    private var fos: FileOutputStream? = null

    override suspend fun fetchPicture(prompt: String): String? {
        val reqBody = ReqBody(
            width = 1152,
            height = 896,
            text_prompts = listOf(TextPrompt(prompt))
        )
        return withContext(Dispatchers.IO) {
            val base64 = async {
                stabilityAPIService.getRecipePicture(engineId, reqBody)
            }.await().awaitResponse().body()?.artifacts?.get(0)?.base64
            return@withContext writeToFile(base64)
        }
    }

    private fun writeToFile(data: String?): String? {
        var returnValue: String? = null
        try {
            if(data != null){
                val decodedString: ByteArray = Base64.decode(data, Base64.DEFAULT)
                val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
                fos = context.openFileOutput("$time.png", Context.MODE_PRIVATE)
                fos?.write(decodedString)
                fos?.flush()
                fos?.close()
                returnValue = "${context.filesDir}${File.separator}$time.png"
                Log.i("writeToFile", returnValue)
            }
        }catch (e: Exception){
            Log.e("writeToFile", e.toString())
        }finally {
            if (fos != null) {
                fos = null
            }
        }
        return  returnValue
    }
}