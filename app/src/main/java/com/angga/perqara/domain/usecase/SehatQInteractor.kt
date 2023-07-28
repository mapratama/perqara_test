package com.angga.perqara.domain.usecase

import com.angga.perqara.data.Resource
import com.angga.perqara.domain.model.Home
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.repository.ISehatQRepository
import kotlinx.coroutines.flow.Flow

class SehatQInteractor(private val sehatQRepository: ISehatQRepository) : SehatQUseCase {

    override fun getHome(): Flow<Resource<Home>> {
        return sehatQRepository.getHome()
    }

    override suspend fun getAllProduct(): Flow<List<Product>> {
        return sehatQRepository.getAllProduct()
    }

    override suspend fun getAllProductLoved(): Flow<List<Product>> {
        return sehatQRepository.getAllProductLoved()
    }

    override suspend fun searchProduct(keyword: String): Flow<List<Product>> {
        return sehatQRepository.searchProduct(keyword)
    }

    override fun setFavoriteProduct(product: Product, state: Int) {
        sehatQRepository.setFavoriteProduct(product, state)
    }
}