package com.sena.crud.data.remote.api

import com.sena.crud.data.remote.dto.req.product.DeleteProductResponseDto
import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.data.remote.dto.req.product.ProductListResponseDto
import com.sena.crud.data.remote.dto.req.product.UpdateProductRequestDto
import com.sena.crud.data.remote.dto.req.product.UpdateProductResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("products/{id}")
    suspend fun GetProductByid(
        @Path("id") id: Int
    ) : Product

    @GET("products")
    suspend fun getAllProducts(): ProductListResponseDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body request: UpdateProductRequestDto
    ): UpdateProductResponseDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): DeleteProductResponseDto
}