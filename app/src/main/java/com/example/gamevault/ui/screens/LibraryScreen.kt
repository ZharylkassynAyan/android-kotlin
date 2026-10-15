package com.example.gamevault.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gamevault.ui.components.EmptyState
import com.example.gamevault.ui.components.GameBottomBar
import com.example.gamevault.ui.components.GameCard
import com.example.gamevault.ui.components.GenreChip
import com.example.gamevault.ui.components.SectionHeader
import com.example.gamevault.ui.previewGames
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing
import com.example.gamevault.viewmodel.GameUiState
import com.example.gamevault.viewmodel.GameViewModel

// ROUTE: the only place that knows the ViewModel.
@Composable
fun LibraryRoute(
    viewModel: GameViewModel,
    onGameClick: (Int) -> Unit,
    onFavouritesClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LibraryScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onGenreSelected = viewModel::onGenreSelected,
        onFavouriteClick = viewModel::onFavouriteClick,
        onGameClick = onGameClick,
        onRetry = viewModel::onRetry,
        onFavouritesTabClick = onFavouritesClick
    )
}

// SCREEN: stateless. Gets state + lambdas, no ViewModel, no logic.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    uiState: GameUiState,
    onSearchChange: (String) -> Unit,
    onGenreSelected: (String) -> Unit,
    onFavouriteClick: (Int) -> Unit,
    onGameClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onFavouritesTabClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("GameVault") }) },
        bottomBar = {
            GameBottomBar(
                isLibrarySelected = true,
                favouriteCount = uiState.favouriteIds.size,
                onLibraryClick = {},
                onFavouritesClick = onFavouritesTabClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Spacing.md)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Search games") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.md))
            SectionHeader(text = "Genres")

            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(uiState.genres) { genre ->
                    GenreChip(
                        text = genre,
                        selected = genre == uiState.selectedGenre,
                        onClick = { onGenreSelected(genre) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.sm))
            SectionHeader(text = "All games")
            Spacer(modifier = Modifier.height(Spacing.sm))

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                uiState.errorMessage != null -> {
                    EmptyState(
                        icon = Icons.Default.Info,
                        title = "Something went wrong",
                        message = uiState.errorMessage,
                        modifier = Modifier.fillMaxSize(),
                        actionText = "Retry",
                        onActionClick = onRetry
                    )
                }
                uiState.games.isEmpty() -> {
                    EmptyState(
                        icon = Icons.Default.Search,
                        title = "No games found",
                        message = "Try a different search or pick another genre.",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(Spacing.md),
                        contentPadding = PaddingValues(bottom = Spacing.md)
                    ) {
                        items(uiState.games, key = { it.id }) { game ->
                            GameCard(
                                game = game,
                                isFavourite = game.id in uiState.favouriteIds,
                                onClick = { onGameClick(game.id) },
                                onFavouriteClick = { onFavouriteClick(game.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------- Previews: fake UiState objects, no ViewModel ----------
private val contentState = GameUiState(
    isLoading = false,
    games = previewGames,
    genres = listOf("All", "Action RPG", "Roguelike"),
    favouriteIds = setOf(1)
)

@Composable
private fun PreviewLibrary(state: GameUiState, darkTheme: Boolean = false) {
    GameVaultTheme(darkTheme = darkTheme) {
        LibraryScreen(
            uiState = state,
            onSearchChange = {}, onGenreSelected = {}, onFavouriteClick = {},
            onGameClick = {}, onRetry = {}, onFavouritesTabClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryLoadingPreview() = PreviewLibrary(GameUiState(isLoading = true))

@Preview(showBackground = true)
@Composable
fun LibraryContentPreview() = PreviewLibrary(contentState)

@Preview(showBackground = true)
@Composable
fun LibraryEmptyPreview() = PreviewLibrary(contentState.copy(games = emptyList(), searchQuery = "zzz"))

@Preview(showBackground = true)
@Composable
fun LibraryErrorPreview() =
    PreviewLibrary(GameUiState(isLoading = false, errorMessage = "Could not load games."))

@Preview(showBackground = true)
@Composable
fun LibraryContentDarkPreview() = PreviewLibrary(contentState, darkTheme = true)