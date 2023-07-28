package com.angga.perqara.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class Product2Response(

    @SerializedName("slug")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val imageUrl: String,
    @SerializedName("released")
    val released: String,
    @SerializedName("rating")
    val rating: Float
)