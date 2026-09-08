package com.sena.crud.domain.useCase

import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int, product: ProductModel): ProductModel {
        return repository.updateProduct(id, product)
    }
}