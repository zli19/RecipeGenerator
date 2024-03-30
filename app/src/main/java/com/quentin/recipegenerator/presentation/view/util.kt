package com.quentin.recipegenerator.presentation.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun converterStringToBitmap(encodedString: String): Bitmap? {
    return try {
        val encodeByte = Base64.decode(encodedString)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e:Exception) {
        e.printStackTrace()
        null
    }
}