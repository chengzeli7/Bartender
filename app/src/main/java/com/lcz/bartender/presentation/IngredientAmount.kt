package com.lcz.bartender.presentation

/**
 * 用于表示鸡尾酒配方中材料及其用量的数据类。
 * @param ingredientId 材料的ID。
 * @param amount 用量。
 * @param unit 单位。
 */
data class IngredientAmount(
    val ingredientId: String, // 引用 IngredientEntity 的 ID
    val amount: String,
    val unit: String
)
