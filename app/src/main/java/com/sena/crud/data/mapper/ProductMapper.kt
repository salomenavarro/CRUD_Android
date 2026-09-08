package com.sena.crud.data.mapper

import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.data.remote.dto.req.product.UpdateProductResponseDto
import com.sena.crud.domain.model.ProductModel


fun Product.toDomain(): ProductModel {
    return ProductModel (
        id =id,
        title = title,
        description = description,
        category = category,
        price = price
    )
}

fun UpdateProductResponseDto.toDomain(): ProductModel {
    return ProductModel(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price
    )
}