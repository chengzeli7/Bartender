package com.lcz.bartender.presentation

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * 数据库中的用户收藏实体，对应产品文档中的“用户收藏数据结构”。
 * @param cocktailId 被收藏酒品的ID，作为主键。
 * @param userId 用户的唯一标识符（可选，如果未来支持多用户或同步）。
 * @param collectionTime 收藏操作发生的时间。
 */
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val cocktailId: String, // 被收藏酒品的ID作为主键
    val userId: String? = null, // 如果未来支持多用户，可以关联用户ID
    val collectionTime: Date // 收藏时间
)
