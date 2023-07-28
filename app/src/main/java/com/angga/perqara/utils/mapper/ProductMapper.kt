package com.angga.perqara.utils.mapper

import com.angga.perqara.data.source.local.entity.ProductEntity
import com.angga.perqara.data.source.remote.response.ProductResponse
import com.angga.perqara.domain.model.Product

object ProductMapper {

    fun mapResponsesToEntities(input: List<ProductResponse>): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        input.map {
            val product = ProductEntity(
                id = it.id,
                title = it.title,
                description = it.description,
                imageUrl = it.imageUrl,
                loved = it.loved,
                price = it.price
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
                description = it.description,
                imageUrl = it.imageUrl,
                loved = it.loved,
                price = it.price
            )
        }

    fun mapDomainToEntity(input: Product) = ProductEntity(
        id = input.id,
        title = input.title,
        description = input.description,
        imageUrl = input.imageUrl,
        loved = input.loved,
        price = input.price
    )

}