package com.example.gamevault.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamevault.data.GameRepository
import com.example.gamevault.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: GameRepository = GameRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var allGames: List<Game> = emptyList()

    init {
        Log.d("GameVault", "GameViewModel init")
        loadGames()
    }

    private fun loadGames() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                allGames = repository.getGames()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        genres = listOf("All") + allGames.map { game -> game.genre }.distinct()
                    ).applyFilters()
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Something went wrong")
                }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.update { it.copy(searchQuery = query).applyFilters() }
    }

    fun onGenreSelected(genre: String) {
        _uiState.update { it.copy(selectedGenre = genre).applyFilters() }
    }

    fun onFavouriteClick(gameId: Int) {
        _uiState.update { state ->
            val newIds = if (gameId in state.favouriteIds) state.favouriteIds - gameId
            else state.favouriteIds + gameId
            state.copy(favouriteIds = newIds).applyFilters()
        }
    }

    fun onRetry() {
        loadGames()
    }

    fun getGame(id: Int): Game? = allGames.find { it.id == id }

    // All filtering lives here, never in a composable.
    private fun GameUiState.applyFilters(): GameUiState {
        val shown = allGames.filter { game ->
            val genreOk = selectedGenre == "All" || game.genre == selectedGenre
            val searchOk = game.title.contains(searchQuery, ignoreCase = true)
            genreOk && searchOk
        }
        val favourites = allGames.filter { it.id in favouriteIds }
        return copy(games = shown, favouriteGames = favourites)
    }

    override fun onCleared() {
        Log.d("GameVault", "GameViewModel onCleared")
        super.onCleared()
    }
}
