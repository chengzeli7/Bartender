package com.lcz.bartender.presentation

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据库中的材料实体，对应产品文档中的“材料数据结构”。
 * @param id 唯一标识符，作为主键。
 * @param name 材料名称。
 * @param type 材料类型（如烈酒、利口酒、辅料、糖浆、果汁等）。
 * @param description 材料的简要描述。
 */
@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val description: String?
)
