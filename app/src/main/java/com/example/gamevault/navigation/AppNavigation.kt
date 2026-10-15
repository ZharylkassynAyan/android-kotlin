package com.example.gamevault.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamevault.ui.screens.FavouritesRoute
import com.example.gamevault.ui.screens.GameDetailRoute
import com.example.gamevault.ui.screens.LibraryRoute
import com.example.gamevault.viewmodel.GameViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // One ViewModel for the whole app. Survives rotation.
    val viewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = "library") {

        composable("library") {
            LibraryRoute(
                viewModel = viewModel,
                onGameClick = { id -> navController.navigate("detail/$id") },
                onFavouritesClick = {
                    navController.navigate("favourites") { launchSingleTop = true }
                }
            )
        }

        composable("favourites") {
            FavouritesRoute(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onGameClick = { id -> navController.navigate("detail/$id") }
            )
        }

        composable(
            route = "detail/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            GameDetailRoute(
                viewModel = viewModel,
                gameId = gameId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}