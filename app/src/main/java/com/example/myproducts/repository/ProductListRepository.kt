package com.example.myproducts.repository

import com.example.myproducts.data.network.ProductListApi
import javax.inject.Inject

class ProductListRepository @Inject constructor(private val api: ProductListApi) : BaseRepository() {
    suspend fun getProducts() = safeApiCall {
        api.getProductList()
    }
}