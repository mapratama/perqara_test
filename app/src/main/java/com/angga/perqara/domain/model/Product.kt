package com.angga.perqara.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product(
    val id: String,
    val title: String,
    val imageUrl: String,
    val rating: Float,
    val releaseDate: String,
    val playtime: Int
): Parcelable