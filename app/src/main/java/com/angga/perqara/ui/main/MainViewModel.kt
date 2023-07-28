package com.angga.perqara.ui.main

import androidx.lifecycle.*
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.usecase.SehatQUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: SehatQUseCase) : ViewModel() {

    val home = useCase.getHome().asLiveData()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun test() {
        useCase.getHome()
    }

    fun getProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllProduct().collect {
                _products.postValue(it)
            }
        }
    }

}