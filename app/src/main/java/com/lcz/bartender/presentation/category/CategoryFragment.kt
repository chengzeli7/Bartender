package com.lcz.bartender.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lcz.bartender.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 显示酒品分类的 Fragment。
 * 使用 @AndroidEntryPoint 注解启用 Hilt 对此 Fragment 的依赖注入。
 */
@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    // 此属性仅在 onCreateView 和 onDestroyView 之间有效。
    private val binding get() = _binding!!

    // 通过 Hilt 注入 ViewModel
    private val categoryViewModel: CategoryViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用 ViewBinding 绑定布局
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 RecyclerView 和适配器
        categoryAdapter = CategoryAdapter { categoryId ->
            // 点击分类项时的回调，导航到鸡尾酒列表页面
            val action = CategoryFragmentDirections.actionNavigationCategoriesToCocktailListFragment(categoryId)
            findNavController().navigate(action)
        }
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context, 2) // 网格布局，每行2列
            adapter = categoryAdapter
        }

        // 观察 ViewModel 中的分类数据
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categories.collect { categories ->
                    categoryAdapter.submitList(categories)
                    // 如果分类列表为空，可以插入一些示例数据（仅用于演示）
                    if (categories.isEmpty()) {
                        categoryViewModel.insertSampleCategories()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 在视图销毁时清除绑定，避免内存泄漏
        _binding = null
    }
}
