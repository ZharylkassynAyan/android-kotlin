package com.example.gamevault.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamevault.ui.screens.FavouritesScreen
import com.example.gamevault.ui.screens.GameDetailScreen
import com.example.gamevault.ui.screens.LibraryScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Shared favourites state: Library, Details and Favourites all read this one list.
    val favouriteIds = remember { mutableStateListOf<Int>() }

    fun toggleFavourite(id: Int) {
        if (favouriteIds.contains(id)) {
            favouriteIds.remove(id)
        } else {
            favouriteIds.add(id)
        }
    }

    NavHost(navController = navController, startDestination = "library") {

        composable("library") {
            LibraryScreen(
                favouriteIds = favouriteIds,
                onGameClick = { id -> navController.navigate("detail/$id") },
                onToggleFavourite = { id -> toggleFavourite(id) },
                onFavouritesClick = {
                    navController.navigate("favourites") { launchSingleTop = true }
                }
            )
        }

        composable("favourites") {
            FavouritesScreen(
                favouriteIds = favouriteIds,
                onBackClick = { navController.popBackStack() },
                onGameClick = { id -> navController.navigate("detail/$id") },
                onToggleFavourite = { id -> toggleFavourite(id) }
            )
        }

        composable(
            route = "detail/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
            GameDetailScreen(
                gameId = gameId,
                isFavourite = favouriteIds.contains(gameId),
                onBackClick = { navController.popBackStack() },
                onToggleFavourite = { toggleFavourite(gameId) }
            )
        }
    }
}