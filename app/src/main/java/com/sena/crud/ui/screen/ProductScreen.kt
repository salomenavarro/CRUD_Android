package com.sena.crud.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.ui.section.ProductDetails
import com.sena.crud.ui.viewModel.ProductViewModel

@Composable
fun ProductScreen(
    productId: Int,
    viewModel: ProductViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }
    ProductDetails(
        uiState = uiState,
        onRetry = { viewModel.getProductById(productId) },
        onUpdate = { updated -> viewModel.updateProduct(updated) }
    )
}