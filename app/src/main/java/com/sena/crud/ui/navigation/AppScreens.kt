package com.sena.crud.ui.navigation

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Detail : AppScreens("detail/{productId}") {
        fun createRoute(productId: Int) = "detail/$productId"
    }
}