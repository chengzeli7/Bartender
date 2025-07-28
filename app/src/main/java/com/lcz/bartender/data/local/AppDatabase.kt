package com.lcz.bartender.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lcz.bartender.data.converter.Converters
import com.lcz.bartender.data.local.dao.CategoryDao
import com.lcz.bartender.data.local.dao.CocktailDao
import com.lcz.bartender.data.local.dao.FavoriteDao
import com.lcz.bartender.presentation.FavoriteEntity
import com.lcz.bartender.presentation.IngredientEntity
import com.lcz.bartender.presentation.category.CategoryEntity
import com.lcz.bartender.presentation.cocktaillist.CocktailEntity

/**
 * Room 数据库抽象类。
 * 定义数据库的版本、实体和类型转换器。
 * @param entities 数据库中包含的所有实体类。
 * @param version 数据库版本。每次数据库 Schema 更改时都需要递增。
 * @param exportSchema 是否将 Schema 导出到文件中。
 */
@Database(
    entities = [
        CategoryEntity::class,
        CocktailEntity::class,
        IngredientEntity::class,
        FavoriteEntity::class
    ],
    version = 1, // 初始版本
    exportSchema = false // 生产环境中通常设置为 false，除非需要进行 Schema 迁移测试
)
@TypeConverters(Converters::class) // 注册类型转换器
abstract class AppDatabase : RoomDatabase() {
    // 抽象方法，返回各个 DAO 接口的实例
    abstract fun categoryDao(): CategoryDao
    abstract fun cocktailDao(): CocktailDao
    abstract fun favoriteDao(): FavoriteDao
    // abstract fun ingredientDao(): IngredientDao // 如果需要直接操作 IngredientEntity，可以添加
}
