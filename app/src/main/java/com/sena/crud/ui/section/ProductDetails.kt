package com.sena.crud.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.component.productCard
import com.sena.crud.ui.state.ProductUIState

@Composable
fun ProductDetails(
    uiState: ProductUIState,
    onRetry: ()-> Unit,
    onUpdate: (ProductModel) -> Unit
){
    var editMode by remember { mutableStateOf(false) }

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }
        uiState.errorMessage != null -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = uiState.errorMessage ?: "Error en la carga del producto")
                Button(onClick = onRetry) {
                    Text(text = "Reintentar")
                }
            }
        }
        uiState.product != null -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (uiState.updateSuccess && !editMode) {
                    Text(text = "Producto actualizado <3")
                }

                if (editMode) {
                    ProductEditForm(
                        product = uiState.product,
                        isUpdating = uiState.isUpdating,
                        onSave = { updated ->
                            onUpdate(updated)
                            editMode = false
                        },
                        onCancel = { editMode = false }
                    )
                } else {
                    productCard(product = uiState.product)
                    Button(onClick = { editMode = true }) {
                        Text(text = "Editar")
                    }
                }
            }
        }
    }
}