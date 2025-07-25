package com.lcz.bartender.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lcz.bartender.presentation.cocktaillist.CocktailEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO 接口，用于访问和操作鸡尾酒配方数据。
 */
@Dao
interface CocktailDao {
    /**
     * 插入所有鸡尾酒配方。如果存在冲突，则替换现有配方。
     * @param cocktails 要插入的鸡尾酒配方列表。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCocktails(cocktails: List<CocktailEntity>)

    /**
     * 获取所有鸡尾酒配方的 Flow。
     * @return 包含所有鸡尾酒配方的 Flow。
     */
    @Query("SELECT * FROM cocktails ORDER BY name ASC")
    fun getAllCocktails(): Flow<List<CocktailEntity>>

    /**
     * 根据分类 ID 获取鸡尾酒配方列表的 Flow。
     * @param categoryId 分类的 ID。
     * @return 包含指定分类下鸡尾酒配方的 Flow。
     */
    @Query("SELECT * FROM cocktails WHERE categoryId = :categoryId ORDER BY name ASC")
    fun getCocktailsByCategoryId(categoryId: String): Flow<List<CocktailEntity>>

    /**
     * 根据 ID 获取单个鸡尾酒配方。
     * @param cocktailId 鸡尾酒配方的 ID。
     * @return 对应的 CocktailEntity 对象，如果不存在则为 null。
     */
    @Query("SELECT * FROM cocktails WHERE id = :cocktailId LIMIT 1")
    suspend fun getCocktailById(cocktailId: String): CocktailEntity?

    /**
     * 根据名称模糊搜索鸡尾酒配方。
     * @param query 搜索查询字符串。
     * @return 包含匹配鸡尾酒配方的 Flow。
     */
    @Query("SELECT * FROM cocktails WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchCocktailsByName(query: String): Flow<List<CocktailEntity>>
}
