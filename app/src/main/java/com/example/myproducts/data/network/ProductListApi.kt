package com.example.myproducts.data.network

import com.example.myproducts.responses.productlist.ProductListResponse
import retrofit2.http.GET

interface ProductListApi {

    @GET("2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getProductList(): ProductListResponse

}