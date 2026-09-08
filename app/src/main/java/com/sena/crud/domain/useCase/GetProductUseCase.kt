package com.sena.crud.domain.useCase


import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        id: Int
    ): ProductModel {
        return repository.GetProductById(id)
    }
}