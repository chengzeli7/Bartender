package com.example.bartender.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.bartender.model.Cocktail
import com.example.bartender.model.Difficulty
import com.example.bartender.model.Ingredient
import org.json.JSONArray

class CocktailRepository private constructor(context: Context) {
    private val cocktails = mutableListOf<Cocktail>()
    private val favorites = mutableSetOf<Int>()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("cocktail_prefs", Context.MODE_PRIVATE)

    init {
        loadFavoritesFromPreferences()
        initSampleData()
    }

    fun getAllCocktails(): List<Cocktail> {
        return cocktails.map { it.copy(isFavorite = favorites.contains(it.id)) }
    }

    fun getPopularCocktails(): List<Cocktail> {
        return getAllCocktails().take(5)
    }

    fun getFavoriteCocktails(): List<Cocktail> {
        return getAllCocktails().filter { favorites.contains(it.id) }
    }

    fun getCocktailById(id: Int): Cocktail? {
        return getAllCocktails().find { it.id == id }
    }

    fun searchCocktails(query: String): List<Cocktail> {
        if (query.isBlank()) return getAllCocktails()
        return getAllCocktails().filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.ingredients.any { ingredient -> ingredient.name.contains(query, ignoreCase = true) }
        }
    }

    fun toggleFavorite(id: Int) {
        if (favorites.contains(id)) {
            favorites.remove(id)
        } else {
            favorites.add(id)
        }
        saveFavoritesToPreferences()
    }

    private fun saveFavoritesToPreferences() {
        val editor = sharedPreferences.edit()
        val jsonArray = JSONArray(favorites.toList())
        editor.putString(FAVORITES_KEY, jsonArray.toString())
        editor.apply()
    }

    private fun loadFavoritesFromPreferences() {
        val jsonString = sharedPreferences.getString(FAVORITES_KEY, null)
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                favorites.add(jsonArray.getInt(i))
            }
        }
    }

    private fun initSampleData() {
        val cocktailEntities = CocktailData.getList()
        cocktails.addAll(cocktailEntities.map { it.toCocktail() })
    }

    private fun CocktailEntity.toCocktail(): Cocktail {
        // 由于id在CocktailRepository中是Int类型，而CocktailData中的是String，这里需要做转换。
        // 为了方便，我们这里取id的hashCode作为Int类型的id，但更严谨的方式是使用一个唯一的Int ID生成策略。
        val idAsInt = this.id.hashCode()

        // 转换难度字符串为Difficulty枚举
        val difficultyEnum = when (this.difficulty) {
            "简单" -> Difficulty.EASY
            "中等" -> Difficulty.MEDIUM
            "复杂" -> Difficulty.HARD
            else -> Difficulty.EASY
        }

        // 转换IngredientAmount列表为Ingredient列表
        val ingredientsList = this.ingredients.map {
            // 这里我们把数量和单位拼接成一个字符串
            val amountString = "${it.amount} ${it.unit}".trim()
            Ingredient(it.ingredientId, amountString)
        }

        return Cocktail(
            id = idAsInt,
            name = this.name,
            description = this.flavorDescription!!.joinToString(", "),
            imageUrl = this.imageUrl,
            difficulty = difficultyEnum,
            ingredients = ingredientsList,
            instructions = this.preparationSteps
        )
    }

    companion object {
        private const val FAVORITES_KEY = "favorites"
        private var instance: CocktailRepository? = null

        fun getInstance(context: Context): CocktailRepository {
            if (instance == null) {
                instance = CocktailRepository(context.applicationContext)
            }
            return instance!!
        }
    }
}