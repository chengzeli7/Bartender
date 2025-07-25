package com.lcz.bartender.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lcz.bartender.presentation.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO 接口，用于访问和操作分类数据。
 */
@Dao
interface CategoryDao {
    /**
     * 插入所有分类。如果存在冲突，则替换现有分类。
     * @param categories 要插入的分类列表。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoryEntity>)

    /**
     * 获取所有分类的 Flow。当数据库中的分类数据发生变化时，Flow 会自动发出新数据。
     * @return 包含所有分类的 Flow。
     */
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    /**
     * 根据 ID 获取单个分类。
     * @param categoryId 分类的 ID。
     * @return 对应的 CategoryEntity 对象，如果不存在则为 null。
     */
    @Query("SELECT * FROM categories WHERE id = :categoryId LIMIT 1")
    suspend fun getCategoryById(categoryId: String): CategoryEntity?
}
