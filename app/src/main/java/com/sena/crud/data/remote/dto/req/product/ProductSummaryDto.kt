package com.sena.crud.data.remote.dto.req.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductSummaryDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "title") val title: String,
    @param:Json(name = "description") val description: String,
    @param:Json(name = "category") val category: String,
    @param:Json(name = "price") val price: Double
)