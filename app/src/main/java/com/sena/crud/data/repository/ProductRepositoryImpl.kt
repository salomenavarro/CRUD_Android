package com.sena.crud.data.repository

import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.remote.api.ProductApiService
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequestDto
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import jakarta.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService
): ProductRepository {
    override suspend fun GetProductById(id: Int): ProductModel {
        val response = api.GetProductByid(id)
        return response.toDomain()
    }

    override suspend fun updateProduct(id: Int, product: ProductModel): ProductModel {
        val request = UpdateProductRequestDto(
            title = product.title,
            description = product.description,
            category = product.category,
            price = product.price
        )
        val response = api.updateProduct(id, request)
        return response.toDomain()
    }
}