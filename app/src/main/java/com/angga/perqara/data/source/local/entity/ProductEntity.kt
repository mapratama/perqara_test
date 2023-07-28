package com.angga.perqara.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val imageUrl: String,
    var loved: Int,
    val price: String,
    val title: String
)