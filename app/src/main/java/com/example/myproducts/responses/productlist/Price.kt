package com.example.myproducts.responses.productlist

data class Price(
    val isOfferPrice: Boolean,
    val message: String,
    val value: Double
): java.io.Serializable