package com.lcz.bartender.data.repository

import com.lcz.bartender.presentation.FavoriteEntity
import com.lcz.bartender.data.local.dao.FavoriteDao
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 收藏数据仓库。
 * 负责管理收藏的鸡尾酒数据。
 * @param favoriteDao FavoriteDao 的实例，通过 Hilt 注入。
 */
@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    /**
     * 获取所有收藏的 Flow。
     * @return 包含所有 FavoriteEntity 列表的 Flow。
     */
    fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }

    /**
     * 添加一个鸡尾酒到收藏。
     * @param cocktailId 要收藏的鸡尾酒 ID。
     */
    suspend fun addFavorite(cocktailId: String) {
        val favorite = FavoriteEntity(cocktailId = cocktailId, collectionTime = Date())
        favoriteDao.insertFavorite(favorite)
    }

    /**
     * 从收藏中移除一个鸡尾酒。
     * @param cocktailId 要移除的鸡尾酒 ID。
     */
    suspend fun removeFavorite(cocktailId: String) {
        favoriteDao.deleteFavoriteById(cocktailId)
    }

    /**
     * 检查某个鸡尾酒是否已被收藏。
     * @param cocktailId 鸡尾酒的 ID。
     * @return 如果已收藏则返回 true，否则返回 false。
     */
    suspend fun isFavorite(cocktailId: String): Boolean {
        return favoriteDao.getFavoriteById(cocktailId) != null
    }

    /**
     * 获取所有收藏的鸡尾酒 ID 列表的 Flow。
     * @return 包含所有收藏鸡尾酒 ID 的 Flow。
     */
    fun getAllFavoriteCocktailIds(): Flow<List<String>> {
        return favoriteDao.getAllFavoriteCocktailIds()
    }
}
