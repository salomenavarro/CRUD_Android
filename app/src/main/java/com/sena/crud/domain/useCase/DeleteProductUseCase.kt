package com.sena.crud.domain.useCase

import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return repository.deleteProduct(id)
    }
}