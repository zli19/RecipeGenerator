package com.quentin.recipegenerator.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import org.checkerframework.common.aliasing.qual.Unique

@Serializable
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val directions: String? = null,
    val info: String? = null,
    val ingredients: String? = null,
    val name: String? = null,
    val nutrition: String? = null,
    val prompt: String? = null,
    var picture: String? = null,
    var requirements: String = "",
    var preferences: List<String> = emptyList(),
    var uid: String? = null
)