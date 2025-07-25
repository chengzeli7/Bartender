package com.lcz.bartender.data.repository

import com.lcz.bartender.presentation.cocktaillist.CocktailEntity
import com.lcz.bartender.data.local.dao.CocktailDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 鸡尾酒数据仓库。
 * 负责从数据源（目前是本地 Room 数据库）获取鸡尾酒数据。
 * @param cocktailDao CocktailDao 的实例，通过 Hilt 注入。
 */
@Singleton
class CocktailRepository @Inject constructor(
    private val cocktailDao: CocktailDao
) {
    /**
     * 获取所有鸡尾酒配方的 Flow。
     * @return 包含所有 CocktailEntity 列表的 Flow。
     */
    fun getAllCocktails(): Flow<List<CocktailEntity>> {
        return cocktailDao.getAllCocktails()
    }

    /**
     * 根据分类 ID 获取鸡尾酒配方列表的 Flow。
     * @param categoryId 分类的 ID。
     * @return 包含指定分类下 CocktailEntity 列表的 Flow。
     */
    fun getCocktailsByCategoryId(categoryId: String): Flow<List<CocktailEntity>> {
        return cocktailDao.getCocktailsByCategoryId(categoryId)
    }

    /**
     * 根据 ID 获取单个鸡尾酒配方。
     * @param cocktailId 鸡尾酒配方的 ID。
     * @return 对应的 CocktailEntity 对象，如果不存在则为 null。
     */
    suspend fun getCocktailById(cocktailId: String): CocktailEntity? {
        return cocktailDao.getCocktailById(cocktailId)
    }

    /**
     * 插入所有鸡尾酒配方。
     * @param cocktails 要插入的鸡尾酒列表。
     */
    suspend fun insertAllCocktails(cocktails: List<CocktailEntity>) {
        cocktailDao.insertAllCocktails(cocktails)
    }

    /**
     * 根据名称模糊搜索鸡尾酒配方。
     * @param query 搜索查询字符串。
     * @return 包含匹配 CocktailEntity 列表的 Flow。
     */
    fun searchCocktailsByName(query: String): Flow<List<CocktailEntity>> {
        return cocktailDao.searchCocktailsByName(query)
    }
}
