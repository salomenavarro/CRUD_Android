package com.sena.crud.data.remote.dto.req.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteProductResponseDto(
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "title")
    val title: String,
    @param:Json(name = "isDeleted")
    val isDeleted: Boolean
)