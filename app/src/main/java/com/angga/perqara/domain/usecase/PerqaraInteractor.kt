package com.angga.perqara.domain.usecase

import com.angga.perqara.data.Resource
import com.angga.perqara.domain.model.Home
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.repository.IPerqaraRepository
import kotlinx.coroutines.flow.Flow

class PerqaraInteractor(private val perqaraRepository: IPerqaraRepository) : PerqaraUseCase {

    override fun getHome(): Flow<Resource<Home>> {
        return perqaraRepository.getHome()
    }

    override suspend fun getAllProduct(): Flow<List<Product>> {
        return perqaraRepository.getAllProduct()
    }

    override suspend fun getAllProductLoved(): Flow<List<Product>> {
        return perqaraRepository.getAllProductLoved()
    }

    override suspend fun searchProduct(keyword: String): Flow<List<Product>> {
        return perqaraRepository.searchProduct(keyword)
    }

    override fun setFavoriteProduct(product: Product, state: Int) {
        perqaraRepository.setFavoriteProduct(product, state)
    }
}