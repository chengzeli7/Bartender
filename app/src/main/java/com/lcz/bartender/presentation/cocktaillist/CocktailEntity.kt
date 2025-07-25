package com.lcz.bartender.presentation.cocktaillist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lcz.bartender.presentation.IngredientAmount

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
@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey val id: String,
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
