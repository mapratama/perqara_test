package com.angga.perqara.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.usecase.SehatQUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class SearchViewModel(private val useCase: SehatQUseCase) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun searchProduct(keyword: String) =
        viewModelScope.launch(Dispatchers.IO) {
            useCase.searchProduct(keyword).collect {
                _products.postValue(it)
            }
        }

}