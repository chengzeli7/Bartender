package com.lcz.bartender.presentation.cocktaillist // 包名已更新

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lcz.bartender.databinding.FragmentCocktailListBinding
import com.lcz.bartender.presentation.CocktailDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 显示特定分类下鸡尾酒列表的 Fragment。
 * 使用 @AndroidEntryPoint 注解启用 Hilt 对此 Fragment 的依赖注入。
 */
@AndroidEntryPoint
class CocktailListFragment : Fragment() {

    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!

    // 通过 Hilt 注入 ViewModel
    private val cocktailListViewModel: CocktailListViewModel by viewModels()

    // 获取导航参数 (categoryId)
    private val args: CocktailListFragmentArgs by navArgs()

    private lateinit var cocktailAdapter: CocktailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = args.categoryId

        // 初始化 RecyclerView 和适配器
        cocktailAdapter = CocktailAdapter(
            onItemClick = { cocktailId ->
                CocktailDetailActivity.actionStart(requireActivity(), cocktailId)
            },
            onFavoriteClick = { cocktailId, isFavorite ->
                // 点击收藏按钮时的回调
                cocktailListViewModel.toggleFavoriteStatus(cocktailId, isFavorite)
            }
        )
        binding.recyclerViewCocktails.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = cocktailAdapter
        }

        // 设置 SearchView 的监听器
        binding.searchViewList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 当用户提交搜索时（例如，按下键盘上的搜索按钮）
                cocktailListViewModel.setSearchQuery(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 当搜索文本改变时实时更新
                cocktailListViewModel.setSearchQuery(newText.orEmpty())
                return true
            }
        })

        // 观察 ViewModel 中的鸡尾酒数据（包含收藏状态）
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cocktailListViewModel.cocktailsWithFavoriteStatus.collect { cocktailsWithStatus ->
                    cocktailAdapter.submitList(cocktailsWithStatus.shuffled())

                    // 如果列表为空，可以插入一些示例数据（仅用于演示）
                    // 只有当 categoryId 为空字符串 AND 搜索查询也为空时才插入示例数据
                    if (cocktailsWithStatus.isEmpty() && binding.searchViewList.query.isNullOrEmpty()) {
                        cocktailListViewModel.insertSampleCocktails(categoryId)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
