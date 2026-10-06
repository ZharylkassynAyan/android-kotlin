package com.example.gamevault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamevault.data.allGames
import com.example.gamevault.ui.components.EmptyState
import com.example.gamevault.ui.components.GameBottomBar
import com.example.gamevault.ui.components.GameCard
import com.example.gamevault.ui.components.SectionHeader
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    favouriteIds: List<Int>,
    onBackClick: () -> Unit,
    onGameClick: (Int) -> Unit,
    onToggleFavourite: (Int) -> Unit
) {
    val favouriteGames = allGames.filter { favouriteIds.contains(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favourites") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        },
        bottomBar = {
            GameBottomBar(
                isLibrarySelected = false,
                favouriteCount = favouriteIds.size,
                onLibraryClick = onBackClick,
                onFavouritesClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Spacing.md)
        ) {
            SectionHeader(text = "Your favourite games")
            Spacer(modifier = Modifier.height(Spacing.sm))

            if (favouriteGames.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Favorite,
                    title = "No favourites yet",
                    message = "Tap the heart on any game to save it here.",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    contentPadding = PaddingValues(bottom = Spacing.md)
                ) {
                    items(favouriteGames, key = { it.id }) { game ->
                        GameCard(
                            game = game,
                            isFavourite = true,
                            onClick = { onGameClick(game.id) },
                            onFavouriteClick = { onToggleFavourite(game.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenPreview() {
    GameVaultTheme {
        FavouritesScreen(
            favouriteIds = listOf(1, 5, 9),
            onBackClick = {}, onGameClick = {}, onToggleFavourite = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesScreenEmptyDarkPreview() {
    GameVaultTheme(darkTheme = true) {
        FavouritesScreen(
            favouriteIds = emptyList(),
            onBackClick = {}, onGameClick = {}, onToggleFavourite = {}
        )
    }
}