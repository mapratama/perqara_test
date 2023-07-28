package com.angga.perqara.utils.mapper

import com.angga.perqara.data.source.local.entity.ProductEntity
import com.angga.perqara.data.source.remote.response.ProductResponse
import com.angga.perqara.domain.model.Product

object ProductMapper {

    fun mapResponsesToEntities(input: List<ProductResponse>): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        input.map {
            val product = ProductEntity(
                id = it.slug,
                title = it.name,
                imageUrl = it.imageUrl,
                releaseDate = it.released,
                rating = it.rating,
                playtime = it.playtime
            )
            productList.add(product)
        }
        return productList
    }

    fun mapEntitiesToDomain(input: List<ProductEntity>): List<Product> =
        input.map {
            Product(
                id = it.id,
                title = it.title,
                imageUrl = it.imageUrl,
                releaseDate = it.releaseDate,
                rating = it.rating,
                playtime = it.playtime
            )
        }
}