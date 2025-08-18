package com.example.bartender.repository

/**
 * 数据库中的鸡尾酒配方实体，对应产品文档中的“酒品配方数据结构”。
 * @param id 唯一标识符，作为主键。
 * @param name 酒品名称。
 * @param categoryId 所属一级分类的ID。
 * @param imageUrl 酒品完成图的URL/本地路径。
 * @param preparationSteps 详细的制作步骤列表。
 * @param ingredients 包含材料ID、用量、单位的对象列表。
 * @param flavorDescription 描述酒品的风味特点。
 * @param difficulty 制作难度。
 * @param alcoholStrength 酒品酒精强度。
 * @param recommendedGlass 建议使用的杯具类型。
 * @param history 背景介绍。
 */
data class CocktailEntity(
    val id: String,
    val name: String,
    val categoryId: String,
    val imageUrl: String,
    val preparationSteps: List<String>,
    val ingredients: List<IngredientAmount>, // 使用 IngredientAmount 列表
    val flavorDescription: List<String>?,
    val difficulty: String?,
    val alcoholStrength: String?,
    val recommendedGlass: String?,
    val history: String?
)

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
