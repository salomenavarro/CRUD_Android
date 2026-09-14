package com.sena.crud.domain.repository

import com.sena.crud.domain.model.ProductModel

interface ProductRepository {
    suspend fun GetProductById(id: Int): ProductModel

    suspend fun getAllProducts(): List<ProductModel>

    suspend fun updateProduct(id: Int, product: ProductModel): ProductModel

    suspend fun deleteProduct(id: Int): Boolean
}