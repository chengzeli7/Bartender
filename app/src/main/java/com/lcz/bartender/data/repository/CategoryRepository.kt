package com.lcz.bartender.data.repository

import com.lcz.bartender.presentation.category.CategoryEntity
import com.lcz.bartender.data.local.dao.CategoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 分类数据仓库。
 * 负责从数据源（目前是本地 Room 数据库）获取分类数据。
 * @param categoryDao CategoryDao 的实例，通过 Hilt 注入。
 */
@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    /**
     * 获取所有分类的 Flow。
     * @return 包含所有 CategoryEntity 列表的 Flow。
     */
    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    /**
     * 插入所有分类。
     * @param categories 要插入的分类列表。
     */
    suspend fun insertAllCategories(categories: List<CategoryEntity>) {
        categoryDao.insertAllCategories(categories)
    }

    /**
     * 根据 ID 获取单个分类。
     * @param categoryId 分类的 ID。
     * @return 对应的 CategoryEntity 对象，如果不存在则为 null。
     */
    suspend fun getCategoryById(categoryId: String): CategoryEntity? {
        return categoryDao.getCategoryById(categoryId)
    }
}
