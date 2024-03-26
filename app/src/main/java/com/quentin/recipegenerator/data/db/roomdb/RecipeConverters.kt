package com.quentin.recipegenerator.data.db.roomdb

import androidx.room.TypeConverter
import com.quentin.recipegenerator.domain.model.Instruction
import com.quentin.recipegenerator.domain.model.Picture
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.serialization.json.Json


class RecipeConverters {

    private val moshi: Moshi = Moshi.Builder().build()

    private val stringListType = Types.newParameterizedType(List::class.java, String::class.javaObjectType)

    @TypeConverter
    fun fromStringListType(value: List<String>): String{
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStringListType(value: String): List<String>{
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        return adapter.fromJson(value)!!
    }

    @TypeConverter
    fun fromInstructionListType(value: List<Instruction>): String{
        val newValue = value.map {
            Json.encodeToString(Instruction.serializer(), it)
        }
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        return adapter.toJson(newValue)
    }

    @TypeConverter
    fun toInstructionListType(value: String): List<Instruction>{
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        val newValue =  adapter.fromJson(value)!!
        return newValue.map {
            Json.decodeFromString(Instruction.serializer(), it)
        }
    }

    @TypeConverter
    fun fromPhotoListType(value: List<Picture>): String{
        val newValue = value.map {
            Json.encodeToString(Picture.serializer(), it)
        }
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        return adapter.toJson(newValue)
    }

    @TypeConverter
    fun toPhotoListType(value: String): List<Picture>{
        val adapter: JsonAdapter<List<String>> = moshi.adapter(stringListType)

        val newValue =  adapter.fromJson(value)!!
        return newValue.map {
            Json.decodeFromString(Picture.serializer(), it)
        }
    }
}
