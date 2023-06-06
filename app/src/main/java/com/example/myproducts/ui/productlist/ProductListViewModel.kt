package com.example.myproducts.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproducts.data.network.Resource
import com.example.myproducts.repository.ProductListRepository
import com.example.myproducts.responses.productlist.Product
import com.example.myproducts.responses.productlist.ProductListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductListRepository) : ViewModel() {

    private val _productListResponse: MutableLiveData<Resource<ProductListResponse>> =
        MutableLiveData()
    val productListResponse: LiveData<Resource<ProductListResponse>>
        get() = _productListResponse
    private val _progressBarObserver = MutableLiveData<Boolean>()
    val progressObserver: LiveData<Boolean>
        get() = _progressBarObserver

    private var _favoriteProductList = MutableLiveData<List<Product>>()
    val favoriteProductList: LiveData<List<Product>>
        get() = _favoriteProductList

    private val _notifyFavoritePosition: MutableLiveData<Int> = MutableLiveData()
    val favoriteNotifyPosition: LiveData<Int>
        get() = _notifyFavoritePosition

    fun productList() {
        if (_productListResponse.value == null) {
            _favoriteProductList = MutableLiveData<List<Product>>()
            _progressBarObserver.value = true
            viewModelScope.launch {
                _productListResponse.value = repository.getProducts()
                _progressBarObserver.value = false
            }
        }
    }

    fun updateFavorites(id: String) {
        val list = _productListResponse.value
        if (list != null && (list is Resource.Success)) {
            val index = list.value.products.indexOfFirst { it.id == id }
            list.value.products[index].isFavorite = !list.value.products[index].isFavorite
            _favoriteProductList.value = list.value.products.filter { it.isFavorite }
            _notifyFavoritePosition.value = index
        }
    }
}