package com.angga.perqara.data.source.local.dao

import androidx.room.*
import com.angga.perqara.data.source.local.entity.ProductEntity
import com.angga.perqara.data.source.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM product INNER JOIN `transaction` ON `transaction`.productId = product.id")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product INNER JOIN `transaction` ON `transaction`.productId = product.id WHERE `transaction`.loved = 1")
    fun getFavorite(): Flow<List<ProductEntity>>

//    @Query("SELECT * FROM `transaction` WHERE `transaction`.productId = productId LIMIT 1")
//    fun getTransaction(productId: Int): Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(transaction: TransactionEntity)

    @Update
    fun updateTransaction(transaction: TransactionEntity)
}