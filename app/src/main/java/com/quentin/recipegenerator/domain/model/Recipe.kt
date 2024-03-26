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
    val serving: Serving,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val name: String,
    var pictures: List<Picture> = emptyList(),
    var time: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
)