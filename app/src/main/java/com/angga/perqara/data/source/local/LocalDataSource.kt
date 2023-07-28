package com.angga.perqara.data.source.local

import com.angga.perqara.data.source.local.dao.ProductDao
import com.angga.perqara.data.source.local.dao.TransactionDao
import com.angga.perqara.data.source.local.entity.ProductEntity
import com.angga.perqara.data.source.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val productDao: ProductDao,
    private val transactionDao: TransactionDao
) {

    fun getAllProduct(): Flow<List<ProductEntity>> = productDao.getAll()

    fun searchProduct(name: String): Flow<List<ProductEntity>> = productDao.getListByTitle(name)

    suspend fun insertProduct(productList: List<ProductEntity>) = productDao.insert(productList)

    fun insertTransaction(productId: String, loved: Int) {
        val transactionEntity = TransactionEntity(productId, loved)
        transactionDao.insert(transactionEntity)
    }

    fun getAllProductPurchased(): Flow<List<ProductEntity>> = transactionDao.getAll()
}