package com.angga.perqara.data.source.local.dao

import androidx.room.*
import com.angga.perqara.data.source.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product GROUP BY id")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE title LIKE :title GROUP BY id")
    fun getListByTitle(title: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productList: List<ProductEntity>)
}