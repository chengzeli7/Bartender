package com.lcz.bartender.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lcz.bartender.presentation.FavoriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO 接口，用于访问和操作收藏数据。
 */
@Dao
interface FavoriteDao {
    /**
     * 插入一个收藏。如果存在冲突，则替换现有收藏。
     * @param favorite 要插入的 FavoriteEntity 对象。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    /**
     * 删除一个收藏。
     * @param favorite 要删除的 FavoriteEntity 对象。
     */
    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    /**
     * 根据鸡尾酒 ID 删除一个收藏。
     * @param cocktailId 要删除的鸡尾酒 ID。
     */
    @Query("DELETE FROM favorites WHERE cocktailId = :cocktailId")
    suspend fun deleteFavoriteById(cocktailId: String)

    /**
     * 获取所有收藏的 Flow。
     * @return 包含所有收藏的 Flow。
     */
    @Query("SELECT * FROM favorites ORDER BY collectionTime DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    /**
     * 检查某个鸡尾酒是否已被收藏。
     * @param cocktailId 鸡尾酒的 ID。
     * @return 如果已收藏则返回 FavoriteEntity，否则返回 null。
     */
    @Query("SELECT * FROM favorites WHERE cocktailId = :cocktailId LIMIT 1")
    suspend fun getFavoriteById(cocktailId: String): FavoriteEntity?

    /**
     * 获取所有收藏的鸡尾酒 ID 列表的 Flow。
     * @return 包含所有收藏鸡尾酒 ID 的 Flow。
     */
    @Query("SELECT cocktailId FROM favorites")
    fun getAllFavoriteCocktailIds(): Flow<List<String>>
}
