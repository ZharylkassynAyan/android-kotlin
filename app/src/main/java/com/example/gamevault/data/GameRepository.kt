package com.example.gamevault.data

import kotlinx.coroutines.delay
import com.example.gamevault.model.Game
import java.io.IOException

class GameRepository {

    // Set to true to test the error screen. It fails ONCE, so Retry then works.
    var shouldFail = false

    suspend fun getGames(): List<Game> {
        delay(1500)   // pretend to be a slow server
        if (shouldFail) {
            shouldFail = false
            throw IOException("Could not load games. Please try again.")
        }
        return allGames
    }
}