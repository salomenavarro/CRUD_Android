package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.useCase.GetProductUseCase
import com.sena.crud.domain.useCase.UpdateProductUseCase
import com.sena.crud.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    fun getProductById(id: Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            try {
                val result = getProductUseCase(id)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        product = result,
                        errorMessage = null
                    )
                }

            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        product = null,
                        errorMessage = e.message?: "Error al cargar el producto"
                    )
                }
            }
        }
    }

    fun updateProduct(product: ProductModel){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUpdating = true,
                    errorMessage = null,
                    updateSuccess = false
                )
            }
            try {
                val result = updateProductUseCase(product.id, product)
                _uiState.update {
                    it.copy(
                        isUpdating = false,
                        product = result,
                        updateSuccess = true
                    )
                }
            } catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isUpdating = false,
                        errorMessage = e.message ?: "Error al actualizar el producto"
                    )
                }
            }
        }
    }
}