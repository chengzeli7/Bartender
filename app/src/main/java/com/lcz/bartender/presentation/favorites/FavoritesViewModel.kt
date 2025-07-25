package com.lcz.bartender.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.bartender.data.repository.CocktailRepository
import com.lcz.bartender.data.repository.FavoriteRepository
import com.lcz.bartender.presentation.cocktaillist.CocktailEntity
import com.lcz.bartender.presentation.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 收藏页面 ViewModel。
 * 负责从 FavoriteRepository 和 CocktailRepository 获取收藏的鸡尾酒数据。
 * @param favoriteRepository FavoriteRepository 的实例，通过 Hilt 注入。
 * @param cocktailRepository CocktailRepository 的实例，通过 Hilt 注入。
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    // MutableStateFlow 用于持有收藏实体列表
    private val _favoriteEntities = MutableStateFlow<List<FavoriteEntity>>(emptyList())

    // MutableStateFlow 用于持有收藏的鸡尾酒实体列表
    private val _favoriteCocktails = MutableStateFlow<List<CocktailEntity>>(emptyList())
    val favoriteCocktails: StateFlow<List<CocktailEntity>> = _favoriteCocktails.asStateFlow()

    init {
        // 在 ViewModel 初始化时加载收藏数据
        loadFavorites()
    }

    /**
     * 加载所有收藏的鸡尾酒。
     * 结合 FavoriteEntity 和 CocktailEntity 来获取完整的鸡尾酒信息。
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getAllFavorites().collect { favoriteList ->
                _favoriteEntities.value = favoriteList
                // 根据收藏的 ID 获取对应的鸡尾酒详情
                val cocktails = favoriteList.mapNotNull { favorite ->
                    cocktailRepository.getCocktailById(favorite.cocktailId)
                }
                _favoriteCocktails.value = cocktails
            }
        }
    }

    /**
     * 从收藏中移除一个鸡尾酒。
     * @param cocktailId 要移除的鸡尾酒 ID。
     */
    fun removeFavorite(cocktailId: String) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(cocktailId)
        }
    }
}
