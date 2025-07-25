package com.lcz.bartender.presentation.cocktaildetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.bartender.data.repository.CocktailRepository
import com.lcz.bartender.data.repository.FavoriteRepository
import com.lcz.bartender.presentation.cocktaillist.CocktailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 鸡尾酒详情页面的 ViewModel。
 * 负责获取单个鸡尾酒的详细数据并管理其收藏状态。
 * @param cocktailRepository CocktailRepository 的实例，通过 Hilt 注入。
 * @param favoriteRepository FavoriteRepository 的实例，通过 Hilt 注入。
 * @param savedStateHandle 用于在进程终止后保存和恢复 UI 状态的句柄。
 */
@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 从 SavedStateHandle 获取 cocktailId
    private val cocktailId: String = savedStateHandle.get<String>("cocktailId")
        ?: throw IllegalArgumentException("cocktailId is required")

    // MutableStateFlow 用于持有当前鸡尾酒详情
    private val _cocktail = MutableStateFlow<CocktailEntity?>(null)
    val cocktail: StateFlow<CocktailEntity?> = _cocktail.asStateFlow()

    // MutableStateFlow 用于持有收藏状态
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        // 在 ViewModel 初始化时加载鸡尾酒详情和收藏状态
        loadCocktailDetail()
        checkFavoriteStatus()
    }

    /**
     * 根据鸡尾酒 ID 从数据仓库加载鸡尾酒详情。
     */
    private fun loadCocktailDetail() {
        viewModelScope.launch {
            _cocktail.value = cocktailRepository.getCocktailById(cocktailId)
        }
    }

    /**
     * 检查当前鸡尾酒的收藏状态。
     */
    private fun checkFavoriteStatus() {
        viewModelScope.launch {
            favoriteRepository.isFavorite(cocktailId).also {
                _isFavorite.value = it
            }
        }
    }

    /**
     * 切换鸡尾酒的收藏状态。
     */
    fun toggleFavoriteStatus() {
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFavorite(cocktailId)
            } else {
                favoriteRepository.addFavorite(cocktailId)
            }
            // 更新收藏状态
            _isFavorite.value = !_isFavorite.value
        }
    }
}
