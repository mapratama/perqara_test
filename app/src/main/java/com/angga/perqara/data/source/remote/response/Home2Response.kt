package com.angga.perqara.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Home2Response(
    @SerializedName("results")
    val products: List<Product2Response>
)