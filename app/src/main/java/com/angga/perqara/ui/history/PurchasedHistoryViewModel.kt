package com.angga.perqara.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angga.perqara.domain.model.Product
import com.angga.perqara.domain.usecase.PerqaraUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PurchasedHistoryViewModel(private val useCase: PerqaraUseCase) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun getProductProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getAllProductLoved().collect {
                _products.postValue(it)
            }
        }
    }

}