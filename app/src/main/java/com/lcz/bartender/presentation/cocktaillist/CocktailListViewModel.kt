package com.lcz.bartender.presentation.cocktaillist // 包名已更新

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.bartender.data.repository.CocktailRepository // 包名已更新
import com.lcz.bartender.data.repository.FavoriteRepository // 包名已更新
import com.lcz.bartender.presentation.CocktailWithFavoriteStatus
import com.lcz.bartender.presentation.IngredientAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest // 新增导入
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 鸡尾酒列表页面的 ViewModel。
 * 负责根据分类ID获取鸡尾酒数据，并处理收藏状态。
 * @param cocktailRepository CocktailRepository 的实例，通过 Hilt 注入。
 * @param favoriteRepository FavoriteRepository 的实例，通过 Hilt 注入。
 * @param savedStateHandle 用于在进程终止后保存和恢复 UI 状态的句柄。
 */
@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 从 SavedStateHandle 获取 categoryId
    private val categoryId: String = savedStateHandle.get<String>("categoryId")
        ?: "" // 提供默认空字符串，避免作为起始目的地时崩溃

    // MutableStateFlow 用于持有当前搜索查询文本
    private val _searchQuery = MutableStateFlow("") // 初始为空字符串，表示没有搜索

    // MutableStateFlow 用于持有收藏的鸡尾酒ID列表
    private val _favoriteCocktailIds = MutableStateFlow<List<String>>(emptyList())

    // 结合搜索查询和收藏ID列表，生成带有收藏状态的鸡尾酒列表
    // 使用 flatMapLatest 监听 _searchQuery 的变化，并根据其值切换数据流
    @OptIn(ExperimentalCoroutinesApi::class)
    val cocktailsWithFavoriteStatus: StateFlow<List<CocktailWithFavoriteStatus>> =
        _searchQuery.flatMapLatest { query ->
            if (query.isEmpty()) {
                // 如果搜索查询为空，根据 categoryId 加载鸡尾酒
                if (categoryId.isEmpty()) {
                    cocktailRepository.getAllCocktails() // 加载所有鸡尾酒
                } else {
                    cocktailRepository.getCocktailsByCategoryId(categoryId) // 加载特定分类鸡尾酒
                }
            } else {
                // 如果有搜索查询，执行模糊搜索
                cocktailRepository.searchCocktailsByName(query)
            }
        }.combine(_favoriteCocktailIds) { cocktails, favoriteIds ->
            cocktails.map { cocktail ->
                CocktailWithFavoriteStatus(
                    cocktail = cocktail,
                    isFavorite = favoriteIds.contains(cocktail.id)
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        // 在 ViewModel 初始化时加载收藏状态
        loadFavoriteCocktailIds()
        // 鸡尾酒数据流现在由 _searchQuery.flatMapLatest 驱动，无需单独调用 loadCocktailsByCategoryId
    }

    /**
     * 设置搜索查询文本。
     * @param query 搜索查询字符串。
     */
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * 加载所有收藏的鸡尾酒 ID。
     */
    private fun loadFavoriteCocktailIds() {
        viewModelScope.launch {
            favoriteRepository.getAllFavoriteCocktailIds().collect {
                _favoriteCocktailIds.value = it
            }
        }
    }

    /**
     * 切换鸡尾酒的收藏状态。
     * @param cocktailId 鸡尾酒的 ID。
     * @param isFavorite 当前是否已收藏。
     */
    fun toggleFavoriteStatus(cocktailId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                favoriteRepository.removeFavorite(cocktailId)
            } else {
                favoriteRepository.addFavorite(cocktailId)
            }
        }
    }

    /**
     * （示例）模拟插入一些初始鸡尾酒数据。
     * 在实际应用中，这些数据可能来自预填充数据库或首次网络同步。
     */
    fun insertSampleCocktails(categoryId: String) {
        viewModelScope.launch {
            val sampleCocktails = when (categoryId) {
                "cocktail" -> CocktailData.getList()

                "wine" -> listOf(
                    CocktailEntity(
                        id = "red_wine",
                        name = "红葡萄酒",
                        categoryId = "wine",
                        imageUrl = "https://placehold.co/150x150/000000/FFFFFF?text=RedWine",
                        preparationSteps = listOf("倒入红葡萄酒。"),
                        ingredients = listOf(
                            IngredientAmount("red_wine", "适量", "")
                        ),
                        flavorDescription = listOf("干", "果香", "单宁"),
                        difficulty = "简单",
                        alcoholStrength = "中度",
                        recommendedGlass = "红酒杯",
                        history = "葡萄酒是世界上最古老的饮品之一。"
                    )
                )

                else -> emptyList()

            }
            Log.d("LCZD", "onViewCreated88888: ${sampleCocktails.size}")

            cocktailRepository.insertAllCocktails(sampleCocktails)
        }
    }
}
