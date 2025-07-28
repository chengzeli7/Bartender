package com.lcz.bartender.di

import android.content.Context
import androidx.room.Room
import com.lcz.bartender.data.local.AppDatabase
import com.lcz.bartender.data.local.dao.CategoryDao
import com.lcz.bartender.data.local.dao.CocktailDao
import com.lcz.bartender.data.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt 模块，用于提供应用程序范围的依赖项。
 * @InstallIn(SingletonComponent::class) 表示此模块中的依赖项在应用程序的整个生命周期中都是单例。
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 提供 Room 数据库的单例实例。
     * @param context 应用程序上下文，由 Hilt 提供。
     * @return AppDatabase 的单例实例。
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "offline_bartender_app_database" // 数据库文件名称
        )
            // 移除 createFromAsset，确保 Room 在首次运行时创建空数据库
            // 应用程序将通过 ViewModel 中的 insertSampleCategories/insertSampleCocktails 方法填充数据
            .fallbackToDestructiveMigration() // 允许破坏性迁移，仅用于开发阶段，生产环境应使用适当的迁移策略
            .build()
    }

    /**
     * 提供 CategoryDao 的实例。
     * @param database AppDatabase 实例，由 Hilt 提供。
     * @return CategoryDao 实例。
     */
    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    /**
     * 提供 CocktailDao 的实例。
     * @param database AppDatabase 实例，由 Hilt 提供。
     * @return CocktailDao 实例。
     */
    @Provides
    fun provideCocktailDao(database: AppDatabase): CocktailDao {
        return database.cocktailDao()
    }

    /**
     * 提供 FavoriteDao 的实例。
     * @param database AppDatabase 实例，由 Hilt 提供。
     * @return FavoriteDao 实例。
     */
    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}
