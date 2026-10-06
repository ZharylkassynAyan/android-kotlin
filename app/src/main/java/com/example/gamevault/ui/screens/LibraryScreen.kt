package com.example.gamevault.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamevault.data.allGames
import com.example.gamevault.ui.components.EmptyState
import com.example.gamevault.ui.components.GameBottomBar
import com.example.gamevault.ui.components.GameCard
import com.example.gamevault.ui.components.GenreChip
import com.example.gamevault.ui.components.SectionHeader
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    favouriteIds: List<Int>,
    onGameClick: (Int) -> Unit,
    onToggleFavourite: (Int) -> Unit,
    onFavouritesClick: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("All") }

    val genres = listOf("All") + allGames.map { it.genre }.distinct()

    val filteredGames = allGames.filter { game ->
        val genreMatches = selectedGenre == "All" || game.genre == selectedGenre
        val searchMatches = game.title.contains(searchText, ignoreCase = true)
        genreMatches && searchMatches
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("GameVault") }) },
        bottomBar = {
            GameBottomBar(
                isLibrarySelected = true,
                favouriteCount = favouriteIds.size,
                onLibraryClick = {},
                onFavouritesClick = onFavouritesClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Spacing.md)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search games") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.md))
            SectionHeader(text = "Genres")

            LazyRow(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                items(genres) { genre ->
                    GenreChip(
                        text = genre,
                        selected = genre == selectedGenre,
                        onClick = { selectedGenre = genre }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.sm))
            SectionHeader(text = "All games")
            Spacer(modifier = Modifier.height(Spacing.sm))

            if (filteredGames.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Search,
                    title = "No games found",
                    message = "Try a different search or pick another genre.",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                    contentPadding = PaddingValues(bottom = Spacing.md)
                ) {
                    items(filteredGames, key = { it.id }) { game ->
                        GameCard(
                            game = game,
                            isFavourite = favouriteIds.contains(game.id),
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
fun LibraryScreenPreview() {
    GameVaultTheme {
        LibraryScreen(
            favouriteIds = listOf(1, 3),
            onGameClick = {},
            onToggleFavourite = {},
            onFavouritesClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenDarkPreview() {
    GameVaultTheme(darkTheme = true) {
        LibraryScreen(
            favouriteIds = listOf(1, 3),
            onGameClick = {},
            onToggleFavourite = {},
            onFavouritesClick = {}
        )
    }
}