package com.example.gamevault.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamevault.data.allGames
import com.example.gamevault.ui.components.EmptyState
import com.example.gamevault.ui.components.InfoCard
import com.example.gamevault.ui.components.SectionHeader
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    isFavourite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavourite: () -> Unit
) {
    // Find the game using the ID that came from navigation.
    val game = allGames.find { it.id == gameId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                actions = {
                    if (game != null) {
                        IconButton(onClick = onToggleFavourite) {
                            Icon(
                                imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavourite) "Remove ${game.title} from favourites"
                                else "Add ${game.title} to favourites",
                                tint = if (isFavourite) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (game == null) {
            EmptyState(
                icon = Icons.Default.Info,
                title = "Game not found",
                message = "This game does not exist.",
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = game.imageRes),
                    contentDescription = "Large cover art of ${game.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                )
                Column(modifier = Modifier.padding(Spacing.md)) {
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                        InfoCard(label = "RATING", value = "${game.rating} ★", modifier = Modifier.weight(1f))
                        InfoCard(label = "YEAR", value = "${game.releaseYear}", modifier = Modifier.weight(1f))
                        InfoCard(label = "GENRE", value = game.genre, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(Spacing.lg))
                    SectionHeader(text = "About the game")
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    Text(
                        text = game.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    GameVaultTheme {
        GameDetailScreen(gameId = 5, isFavourite = true, onBackClick = {}, onToggleFavourite = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailScreenDarkPreview() {
    GameVaultTheme(darkTheme = true) {
        GameDetailScreen(gameId = 2, isFavourite = false, onBackClick = {}, onToggleFavourite = {})
    }
}