package com.lcz.bartender.presentation.category

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据库中的分类实体，对应产品文档中的“酒品分类数据结构”。
 * @param id 唯一标识符，作为主键。
 * @param name 分类名称。
 * @param imageUrl 分类代表性图标或图片URL/本地路径。
 * @param description 分类的简要描述。
 */
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val description: String?
)
