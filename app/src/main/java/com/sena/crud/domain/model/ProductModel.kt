package com.sena.crud.domain.model

data class ProductModel (
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double
)