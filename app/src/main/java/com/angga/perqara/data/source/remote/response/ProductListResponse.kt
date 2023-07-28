package com.angga.perqara.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("results")
    val products: List<ProductResponse>
)