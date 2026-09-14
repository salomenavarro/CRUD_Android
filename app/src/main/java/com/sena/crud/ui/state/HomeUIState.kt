package com.sena.crud.ui.state

import com.sena.crud.domain.model.ProductModel

data class HomeUIState(
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val errorMessage: String? = null
)