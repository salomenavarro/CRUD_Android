package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.section.ProductDetails
import com.sena.crud.ui.viewModel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    productId: Int,
    onBack: () -> Unit,
    onProductDeleted: (Int) -> Unit = {},
    onProductUpdated: (ProductModel) -> Unit = {},
    knownProduct: ProductModel? = null,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productId, knownProduct) {
        if (knownProduct != null) {
            // Ya tenemos este producto (con ediciones locales aplicadas) desde
            // Home: lo usamos directo y nos ahorramos pedirlo al servidor, que
            // nos devolvería la versión vieja porque dummyjson no persiste updates.
            viewModel.setKnownProduct(knownProduct)
        } else {
            // No lo teníamos (ej. entraste directo a este ID): sí lo pedimos por API.
            viewModel.getProductById(productId)
        }
    }

    LaunchedEffect(uiState.deleteSuccess) {
        if (uiState.deleteSuccess) {
            onProductDeleted(productId)
            onBack()
        }
    }

    LaunchedEffect(uiState.updateSuccess) {
        if (uiState.updateSuccess) {
            uiState.product?.let { onProductUpdated(it) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ProductDetails(
                uiState = uiState,
                onRetry = { viewModel.getProductById(productId) },
                onUpdate = { updated -> viewModel.updateProduct(updated) },
                onDelete = { id -> viewModel.deleteProduct(id) }
            )
        }
    }
}