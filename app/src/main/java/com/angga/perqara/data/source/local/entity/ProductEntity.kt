package com.angga.perqara.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val releaseDate: String,
    val playtime: Int
)