package com.example.gamevault.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamevault.ui.theme.GameVaultTheme

@Composable
fun GameBottomBar(
    isLibrarySelected: Boolean,
    favouriteCount: Int,
    onLibraryClick: () -> Unit,
    onFavouritesClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = isLibrarySelected,
            onClick = onLibraryClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Library") },
            label = { Text("Library") }
        )
        NavigationBarItem(
            selected = !isLibrarySelected,
            onClick = onFavouritesClick,
            icon = {
                BadgedBox(
                    badge = {
                        if (favouriteCount > 0) {
                            Badge { Text("$favouriteCount") }
                        }
                    }
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favourites")
                }
            },
            label = { Text("Favourites") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameBottomBarPreview() {
    GameVaultTheme {
        GameBottomBar(
            isLibrarySelected = true,
            favouriteCount = 3,
            onLibraryClick = {},
            onFavouritesClick = {}
        )
    }
}