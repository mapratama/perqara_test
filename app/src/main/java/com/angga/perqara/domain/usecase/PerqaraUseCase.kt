package com.angga.perqara.domain.usecase

import com.angga.perqara.data.Resource
import com.angga.perqara.domain.model.Home
import com.angga.perqara.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface PerqaraUseCase {

    fun getHome(): Flow<Resource<Home>>

    suspend fun getAllProduct(): Flow<List<Product>>

    suspend fun getAllProductLoved(): Flow<List<Product>>

    suspend fun searchProduct(keyword: String): Flow<List<Product>>

    fun setFavoriteProduct(product: Product, state: Int)
}