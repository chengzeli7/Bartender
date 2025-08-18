package com.example.bartender.model

data class Cocktail(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val difficulty: Difficulty,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
    var isFavorite: Boolean = false
)

data class Ingredient(
    val name: String,
    val amount: String
)

enum class Difficulty {
    EASY, MEDIUM, HARD
}