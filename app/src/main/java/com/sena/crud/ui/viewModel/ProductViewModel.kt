package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.useCase.DeleteProductUseCase
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
    private val deleteProductUseCase: DeleteProductUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    /**
     * Usa un producto ya conocido (por ejemplo, el que ya está en la lista de Home
     * con ediciones locales aplicadas) en vez de pedirlo al servidor. Evita que
     * la respuesta "stale" de dummyjson (que no persiste updates) pise los
     * cambios que ya hicimos localmente.
     */
    fun setKnownProduct(product: ProductModel) {
        _uiState.update {
            it.copy(
                isLoading = false,
                product = product,
                errorMessage = null
            )
        }
    }

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

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isDeleting = true, errorMessage = null)
            }
            try {
                deleteProductUseCase(id)
                _uiState.update {
                    it.copy(isDeleting = false, deleteSuccess = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        errorMessage = e.message ?: "Error al eliminar el producto"
                    )
                }
            }
        }
    }
}