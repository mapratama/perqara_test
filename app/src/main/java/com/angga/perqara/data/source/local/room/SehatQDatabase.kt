package com.angga.perqara.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angga.perqara.data.source.local.dao.ProductDao
import com.angga.perqara.data.source.local.dao.TransactionDao
import com.angga.perqara.data.source.local.entity.ProductEntity
import com.angga.perqara.data.source.local.entity.TransactionEntity

@Database(
    entities = [ProductEntity::class, TransactionEntity::class],
    version = 4,
    exportSchema = false
)
abstract class SehatQDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao

}