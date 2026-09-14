package com.sena.crud.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.component.productCard
import com.sena.crud.ui.state.ProductUIState

@Composable
fun ProductDetails(
    uiState: ProductUIState,
    onRetry: () -> Unit,
    onUpdate: (ProductModel) -> Unit,
    onDelete: (Int) -> Unit
) {
    var editMode by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Eliminar producto") },
            text = { Text("¿Seguro que quieres eliminar este producto? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    uiState.product?.let { onDelete(it.id) }
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancelar") }
            }
        )
    }

    when {
        uiState.isLoading -> {
            CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        }
        uiState.errorMessage != null -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = uiState.errorMessage ?: "Error en la carga del producto")
                Button(onClick = onRetry) { Text(text = "Reintentar") }
            }
        }
        uiState.product != null -> {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (uiState.updateSuccess && !editMode) {
                    Text(text = "✅ Producto actualizado")
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

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = { editMode = true }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                            Text(text = " Editar")
                        }

                        if (uiState.isDeleting) {
                            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                        } else {
                            Button(
                                onClick = { showDeleteConfirm = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Text(text = " Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}