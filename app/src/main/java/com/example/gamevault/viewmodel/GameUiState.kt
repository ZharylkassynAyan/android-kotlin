package com.example.gamevault.viewmodel

import com.example.gamevault.model.Game

data class GameUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val games: List<Game> = emptyList(),           // after search + genre filter
    val favouriteGames: List<Game> = emptyList(),
    val genres: List<String> = listOf("All"),
    val searchQuery: String = "",
    val selectedGenre: String = "All",
    val favouriteIds: Set<Int> = emptySet()
)