package com.sena.crud.data.remote.dto.req.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListResponseDto(
    @param:Json(name = "products")
    val products: List<ProductSummaryDto>,
    @param:Json(name = "total")
    val total: Int,
    @param:Json(name = "skip")
    val skip: Int,
    @param:Json(name = "limit")
    val limit: Int
)