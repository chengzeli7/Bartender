package com.lcz.bartender.presentation

import com.lcz.bartender.presentation.cocktaillist.CocktailEntity

/**
 * 带有收藏状态的鸡尾酒数据类。
 * 用于在 UI 中显示鸡尾酒及其收藏状态。
 * @param cocktail 鸡尾酒实体。
 * @param isFavorite 是否已收藏。
 */
data class CocktailWithFavoriteStatus(
    val cocktail: CocktailEntity,
    val isFavorite: Boolean
)
