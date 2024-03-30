package com.quentin.recipegenerator.data.db.roomdb

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RecipeConverters {

    private val moshi: Moshi = Moshi.Builder().build()

    private val stringListType = Types.newParameterizedType(List::class.java, String::class.javaObjectType)

    @TypeConverter
    fun toStringListType(value: String): List<String> {
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)
        return adapter.fromJson(value)!!
    }

    @TypeConverter
    fun fromStringListType(list: List<String>): String {
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)
        return adapter.toJson(list)
    }

//    @TypeConverter
//    fun fromPhotoListType(value: List<Picture>): String{
//        val newValue = value.map {
//            Json.encodeToString(Picture.serializer(), it)
//        }
//        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)
//
//        return adapter.toJson(newValue)
//    }
//
//    @TypeConverter
//    fun toPhotoListType(value: String): List<Picture>{
//        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)
//
//        val newValue =  adapter.fromJson(value)!!
//        return newValue.map {
//            Json.decodeFromString(Picture.serializer(), it)
//        }
//    }
}
