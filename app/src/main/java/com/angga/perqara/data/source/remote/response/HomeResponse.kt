package com.angga.perqara.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("productPromo")
    val products: List<ProductResponse>
)