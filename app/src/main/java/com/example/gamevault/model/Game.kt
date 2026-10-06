package com.example.gamevault.model

data class Game(
    val id: Int,
    val title: String,
    val genre: String,
    val rating: Double,
    val releaseYear: Int,
    val description: String,
    val imageRes: Int
)