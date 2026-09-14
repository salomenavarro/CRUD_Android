package com.sena.crud.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.sena.crud.ui.screen.HomeScreen
import com.sena.crud.ui.screen.ProductScreen
import com.sena.crud.ui.viewModel.HomeViewModel

@Composable
fun NavGraph() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route
    ) {
        composable(route = AppScreens.Home.route) {
            HomeScreen(
                onProductClick = { id ->
                    navController.navigate(AppScreens.Detail.createRoute(id))
                }
            )
        }

        composable(
            route = AppScreens.Detail.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0

            // Reutilizamos la MISMA instancia de HomeViewModel (scoped a la entrada
            // "home" del back stack) para poder actualizar su lista en memoria
            // cuando eliminamos o actualizamos un producto, sin depender de que
            // el backend (dummyjson) persista realmente el cambio.
            val homeBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AppScreens.Home.route)
            }
            val homeViewModel: HomeViewModel = hiltViewModel(homeBackStackEntry)
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val knownProduct = remember(homeUiState.products, productId) {
                homeUiState.products.find { it.id == productId }
            }

            ProductScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onProductDeleted = { id -> homeViewModel.removeProductLocally(id) },
                onProductUpdated = { product -> homeViewModel.updateProductLocally(product) },
                knownProduct = knownProduct
            )
        }
    }
}