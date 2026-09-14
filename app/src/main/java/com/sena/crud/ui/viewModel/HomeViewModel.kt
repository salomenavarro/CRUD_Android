package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.useCase.GetAllProductsUseCase
import com.sena.crud.ui.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = getAllProductsUseCase()
                _uiState.update {
                    it.copy(isLoading = false, products = result)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar los productos"
                    )
                }
            }
        }
    }

    /**
     * Elimina el producto de la lista en memoria sin volver a consultar el backend.
     * Necesario porque dummyjson.com no persiste realmente los DELETE.
     */
    fun removeProductLocally(id: Int) {
        _uiState.update { state ->
            state.copy(products = state.products.filterNot { it.id == id })
        }
    }

    /**
     * Reemplaza el producto actualizado en la lista en memoria sin volver a consultar el backend.
     * Necesario porque dummyjson.com no persiste realmente los PUT.
     */
    fun updateProductLocally(product: ProductModel) {
        _uiState.update { state ->
            state.copy(products = state.products.map { if (it.id == product.id) product else it })
        }
    }
}