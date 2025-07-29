package com.lcz.bartender.presentation.favorites

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcz.bartender.databinding.FragmentFavoritesBinding
import com.lcz.bartender.presentation.CocktailDetailActivity
import com.lcz.bartender.presentation.CocktailWithFavoriteStatus
import com.lcz.bartender.presentation.cocktaillist.CocktailAdapter
import com.lcz.bartender.presentation.cocktaillist.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 显示用户收藏的鸡尾酒列表的 Fragment。
 * 使用 @AndroidEntryPoint 注解启用 Hilt 对此 Fragment 的依赖注入。
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    // 通过 Hilt 注入 ViewModel
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private lateinit var cocktailAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 RecyclerView 和适配器
        cocktailAdapter = FavoritesAdapter(
            onItemClick = { cocktailId ->
                CocktailDetailActivity.actionStart(requireActivity(), cocktailId)
            },
            onFavoriteClick = { cocktailId, isFavorite ->
                // 点击收藏按钮时的回调，在收藏页面点击则直接移除收藏
                if (isFavorite) {
                    favoritesViewModel.removeFavorite(cocktailId)
                }
            }
        )
        binding.recyclerViewFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cocktailAdapter
        }

        // 观察 ViewModel 中的收藏鸡尾酒数据
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesViewModel.favoriteCocktails.collect { favoriteCocktails ->
                    // 将 CocktailEntity 转换为 CocktailWithFavoriteStatus，因为收藏页面所有项都是已收藏
                    val cocktailsWithStatus =
                        favoriteCocktails.map { CocktailWithFavoriteStatus(it, true) }
                    cocktailAdapter.submitList(cocktailsWithStatus)
                    // 显示或隐藏“无收藏”提示
                    binding.textNoFavorites.visibility =
                        if (cocktailsWithStatus.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
