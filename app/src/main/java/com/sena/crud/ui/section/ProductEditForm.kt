package com.sena.crud.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel

@Composable
fun ProductEditForm(
    product: ProductModel,
    isUpdating: Boolean,
    onSave: (ProductModel) -> Unit,
    onCancel: () -> Unit
){
    var title by remember { mutableStateOf(product.title) }
    var description by remember { mutableStateOf(product.description) }
    var category by remember { mutableStateOf(product.category) }
    var priceText by remember { mutableStateOf(product.price.toString()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        if (isUpdating) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    onSave(
                        product.copy(
                            title = title,
                            description = description,
                            category = category,
                            price = priceText.toDoubleOrNull() ?: product.price
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
            Button(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}