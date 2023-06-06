package com.example.myproducts.responses.productlist

data class PurchaseType(
    val cartQty: Int,
    val displayName: String,
    val maxQtyLimit: Int,
    val minQtyLimit: Int,
    val purchaseType: String,
    val unitPrice: Double
): java.io.Serializable