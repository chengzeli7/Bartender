package com.lcz.bartender.presentation.cocktaillist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.bartender.data.repository.CocktailRepository
import com.lcz.bartender.data.repository.FavoriteRepository
import com.lcz.bartender.presentation.CocktailWithFavoriteStatus
import com.lcz.bartender.presentation.IngredientAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
        ?: throw IllegalArgumentException("categoryId is required")

    // MutableStateFlow 用于持有鸡尾酒列表
    private val _cocktails = MutableStateFlow<List<CocktailEntity>>(emptyList())

    // 修复 CockileEntity 拼写错误为 CocktailEntity
    val cocktails: StateFlow<List<CocktailEntity>> = _cocktails.asStateFlow()

    // MutableStateFlow 用于持有收藏的鸡尾酒ID列表
    private val _favoriteCocktailIds = MutableStateFlow<List<String>>(emptyList())

    // 结合鸡尾酒列表和收藏ID列表，生成带有收藏状态的鸡尾酒列表
    val cocktailsWithFavoriteStatus: StateFlow<List<CocktailWithFavoriteStatus>> =
        _cocktails.combine(_favoriteCocktailIds) { cocktails, favoriteIds ->
            cocktails.map { cocktail ->
                CocktailWithFavoriteStatus(
                    cocktail = cocktail,
                    isFavorite = favoriteIds.contains(cocktail.id)
                )
            }
        }.stateIn( // 使用 stateIn 将 Flow 转换为 StateFlow
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), // 当有订阅者时保持活跃，并在最后一个订阅者消失后保持活跃 5 秒
            emptyList() // 初始值
        )

    init {
        // 在 ViewModel 初始化时加载鸡尾酒数据和收藏状态
        loadCocktailsByCategoryId(categoryId)
        loadFavoriteCocktailIds()
    }

    /**
     * 根据分类 ID 从数据仓库加载鸡尾酒数据。
     * @param categoryId 分类的 ID。
     */
    private fun loadCocktailsByCategoryId(categoryId: String) {
        viewModelScope.launch {
            cocktailRepository.getCocktailsByCategoryId(categoryId).collect {
                _cocktails.value = it
            }
        }
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
                "cocktail" -> listOf(
                    CocktailEntity(
                        id = "mojito",
                        name = "莫吉托",
                        categoryId = "cocktail",
                        imageUrl = "https://placehold.co/150x150/000000/FFFFFF?text=Mojito",
                        preparationSteps = listOf(
                            "将薄荷叶和青柠角放入杯中...",
                            "加入朗姆酒和糖浆...",
                            "倒入苏打水并搅拌。"
                        ),
                        ingredients = listOf(
                            IngredientAmount("rum", "2", "oz"),
                            IngredientAmount("lime_juice", "1", "oz"),
                            IngredientAmount("mint_leaves", "6", "片"),
                            IngredientAmount("sugar_syrup", "0.75", "oz"),
                            IngredientAmount("soda_water", "适量", "")
                        ),
                        flavorDescription = listOf("甜", "酸", "清爽", "草本", "柑橘"),
                        difficulty = "简单",
                        alcoholStrength = "中度",
                        recommendedGlass = "海波杯",
                        history = "起源于古巴，是海明威最爱的饮品之一。"
                    ),
                    CocktailEntity(
                        id = "margarita",
                        name = "玛格丽特",
                        categoryId = "cocktail",
                        imageUrl = "https://placehold.co/150x150/000000/FFFFFF?text=Margarita",
                        preparationSteps = listOf(
                            "用盐在杯沿做装饰...",
                            "将龙舌兰、橙味利口酒和青柠汁混合...",
                            "摇匀并倒入杯中。"
                        ),
                        ingredients = listOf(
                            IngredientAmount("tequila", "2", "oz"),
                            IngredientAmount("orange_liqueur", "1", "oz"),
                            IngredientAmount("lime_juice", "0.75", "oz")
                        ),
                        flavorDescription = listOf("酸", "甜", "柑橘", "烈性"),
                        difficulty = "中等",
                        alcoholStrength = "烈性",
                        recommendedGlass = "玛格丽特杯",
                        history = "关于玛格丽特的起源有多种说法，最流行的是在墨西哥发明。"
                    )
                )

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
            cocktailRepository.insertAllCocktails(sampleCocktails)

        }
    }
}
