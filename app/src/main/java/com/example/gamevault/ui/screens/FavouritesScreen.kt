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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gamevault.ui.components.EmptyState
import com.example.gamevault.ui.components.GameBottomBar
import com.example.gamevault.ui.components.GameCard
import com.example.gamevault.ui.components.SectionHeader
import com.example.gamevault.ui.previewGames
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing
import com.example.gamevault.viewmodel.GameUiState
import com.example.gamevault.viewmodel.GameViewModel

@Composable
fun FavouritesRoute(
    viewModel: GameViewModel,
    onBackClick: () -> Unit,
    onGameClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FavouritesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onGameClick = onGameClick,
        onFavouriteClick = viewModel::onFavouriteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    uiState: GameUiState,
    onBackClick: () -> Unit,
    onGameClick: (Int) -> Unit,
    onFavouriteClick: (Int) -> Unit
) {
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
                favouriteCount = uiState.favouriteIds.size,
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

            if (uiState.favouriteGames.isEmpty()) {
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
                    items(uiState.favouriteGames, key = { it.id }) { game ->
                        GameCard(
                            game = game,
                            isFavourite = true,
                            onClick = { onGameClick(game.id) },
                            onFavouriteClick = { onFavouriteClick(game.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesContentPreview() {
    GameVaultTheme {
        FavouritesScreen(
            uiState = GameUiState(isLoading = false, favouriteGames = previewGames, favouriteIds = setOf(1, 2, 3)),
            onBackClick = {}, onGameClick = {}, onFavouriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavouritesEmptyDarkPreview() {
    GameVaultTheme(darkTheme = true) {
        FavouritesScreen(
            uiState = GameUiState(isLoading = false),
            onBackClick = {}, onGameClick = {}, onFavouriteClick = {}
        )
    }
}