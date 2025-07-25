package com.lcz.bartender.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.bartender.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 分类页面的 ViewModel。
 * 负责从 CategoryRepository 获取分类数据并暴露给 UI。
 * @param categoryRepository CategoryRepository 的实例，通过 Hilt 注入。
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // MutableStateFlow 用于持有分类列表，并允许在 ViewModel 内部更新
    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    // StateFlow 暴露给 UI，只读，确保数据单向流动
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()

    init {
        // 在 ViewModel 初始化时加载分类数据
        loadCategories()
    }

    /**
     * 从数据仓库加载所有分类。
     * 使用 viewModelScope 启动协程，确保在 ViewModel 生命周期内执行。
     */
    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect {
                _categories.value = it
            }
        }
    }

    /**
     * （示例）模拟插入一些初始分类数据。
     * 在实际应用中，这些数据可能来自预填充数据库或首次网络同步。
     */
    fun insertSampleCategories() {
        viewModelScope.launch {
            val sampleCategories = listOf(
                CategoryEntity("cocktail", "鸡尾酒", "https://placehold.co/100x100/000000/FFFFFF?text=C", "多种基酒与辅料混合的创意饮品"),
                CategoryEntity("wine", "葡萄酒", "https://placehold.co/100x100/000000/FFFFFF?text=W", "由葡萄发酵制成的饮品"),
                CategoryEntity("beer", "啤酒", "https://placehold.co/100x100/000000/FFFFFF?text=B", "由谷物发酵制成的饮品"),
                CategoryEntity("spirit", "白酒", "https://placehold.co/100x100/000000/FFFFFF?text=S", "高度蒸馏酒"),
                CategoryEntity("non_alcoholic", "无酒精饮品", "https://placehold.co/100x100/000000/FFFFFF?text=NA", "不含酒精的混合饮品")
            )
            categoryRepository.insertAllCategories(sampleCategories)
        }
    }
}
