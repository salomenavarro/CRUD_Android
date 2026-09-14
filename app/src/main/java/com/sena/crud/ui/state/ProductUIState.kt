package com.sena.crud.ui.state

import com.sena.crud.domain.model.ProductModel

data class ProductUIState(
    val isLoading: Boolean = false,
    val product: ProductModel? = null,
    val errorMessage: String? = null,
    val isUpdating: Boolean = false,
    val updateSuccess: Boolean = false,
    val isDeleting: Boolean = false,
    val deleteSuccess: Boolean = false
)