package com.quentin.recipegenerator.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val directions: String = "",
    val info: String = "",
    val ingredients: String = "",
    val name: String = "",
    val nutrition: String = "",
    val prompt: String = "",
    var picture: String? = null,
    var requirements: String = "",
    var preferences: List<String> = emptyList()
)