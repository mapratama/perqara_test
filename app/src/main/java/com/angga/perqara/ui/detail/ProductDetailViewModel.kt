package com.angga.perqara.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.usecase.SehatQUseCase

class ProductDetailViewModel(private val useCase: SehatQUseCase) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    fun setProduct(product: Product) {
        _product.value = product
    }

    fun setFavoriteProduct(product: Product, isLoved: Boolean) {
        val status = if (isLoved) 1 else 0
        useCase.setFavoriteProduct(product, status)
    }
}