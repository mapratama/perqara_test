package com.angga.perqara.data.source

import com.angga.perqara.data.NetworkBoundResource
import com.angga.perqara.data.Resource
import com.angga.perqara.data.source.local.LocalDataSource
import com.angga.perqara.data.source.remote.RemoteDataSource
import com.angga.perqara.data.source.remote.network.ApiResponse
import com.angga.perqara.data.source.remote.response.ProductListResponse
import com.angga.perqara.domain.model.Home
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.repository.IPerqaraRepository
import com.angga.perqara.utils.AppExecutors
import com.angga.perqara.utils.mapper.ProductMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PerqaraRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPerqaraRepository {

    override fun getHome(): Flow<Resource<Home>> =
        object : NetworkBoundResource<Home, ProductListResponse>() {
            override fun loadFromDB(): Flow<Home> {
                val home = Home(products = arrayListOf())
                return flowOf(home)
            }

            override fun shouldFetch(data: Home?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<ProductListResponse>> {
                return remoteDataSource.getHome()
            }

            override suspend fun saveCallResult(data: ProductListResponse) {
                val productEntities = ProductMapper.mapResponsesToEntities(data.products)
                localDataSource.insertProduct(productEntities)
            }

        }.asFlow()


    override suspend fun getAllProduct(): Flow<List<Product>> {
        return localDataSource.getAllProduct().map { ProductMapper.mapEntitiesToDomain(it) }
    }

    override suspend fun getAllProductLoved(): Flow<List<Product>> {
        return localDataSource.getAllProductPurchased().map { ProductMapper.mapEntitiesToDomain(it) }
    }

    override suspend fun searchProduct(keyword: String): Flow<List<Product>> {
        return localDataSource.searchProduct("%$keyword%").map { ProductMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteProduct(product: Product, state: Int) {
        appExecutors.diskIO().execute { localDataSource.insertTransaction(product.id, state) }
    }
}