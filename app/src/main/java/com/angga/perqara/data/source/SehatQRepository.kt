package com.angga.perqara.data.source

import com.angga.perqara.data.NetworkBoundResource
import com.angga.perqara.data.Resource
import com.angga.perqara.data.source.local.LocalDataSource
import com.angga.perqara.data.source.remote.RemoteDataSource
import com.angga.perqara.data.source.remote.network.ApiResponse
import com.angga.perqara.data.source.remote.response.Home2Response
import com.angga.perqara.domain.model.Home
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.repository.ISehatQRepository
import com.angga.perqara.utils.AppExecutors
import com.angga.perqara.utils.mapper.ProductMapper
import com.angga.perqara.utils.mapper.ProductMapper2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SehatQRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ISehatQRepository {

//    override fun getHome(): Flow<Resource<Home>> =
//        object : NetworkBoundResource<Home, Home2Response>() {
//            override fun loadFromDB(): Flow<Home> {
//                val home = Home(products = arrayListOf())
//                return flowOf(home)
//            }
//
//            override fun shouldFetch(data: Home?): Boolean {
//                return true
//            }
//
//            override suspend fun createCall(): Flow<ApiResponse<Home2Response>> {
//                return remoteDataSource.getHome()
//            }
//
//            override suspend fun saveCallResult(data: Home2Response) {
//                val productEntities = ProductMapper2.mapResponsesToEntities(data.products)
//                localDataSource.insertProduct(productEntities)
//            }
//
//        }.asFlow()


    override fun getHome(): Flow<Resource<Home>> =
        object : NetworkBoundResource<Home, Home2Response>() {
            override fun loadFromDB(): Flow<Home> {
                val home = Home(products = arrayListOf())
                return flowOf(home)
            }

            override fun shouldFetch(data: Home?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<Home2Response>> {
                return remoteDataSource.getHome()
            }

            override suspend fun saveCallResult(data: Home2Response) {
                val productEntities = ProductMapper2.mapResponsesToEntities(data.products)
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