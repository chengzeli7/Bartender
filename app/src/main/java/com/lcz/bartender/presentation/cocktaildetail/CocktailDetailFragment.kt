package com.lcz.bartender.presentation.cocktaildetail

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lcz.bartender.R
import com.lcz.bartender.databinding.FragmentCocktailDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * 显示单个鸡尾酒详细信息的 Fragment。
 * 使用 @AndroidEntryPoint 注解启用 Hilt 对此 Fragment 的依赖注入。
 */
@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {

    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!

    // 通过 Hilt 注入 ViewModel
    private val cocktailDetailViewModel: CocktailDetailViewModel by viewModels()

    // 获取导航参数 (cocktailId)
    private val args: CocktailDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 观察 ViewModel 中的鸡尾酒详情数据
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cocktailDetailViewModel.cocktail.collect { cocktail ->
                    cocktail?.let {
                        // 填充 UI 元素
                        binding.cocktailName.text = it.name
                        Glide.with(binding.cocktailImage.context)
                            .load(it.imageUrl)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .error(android.R.drawable.ic_menu_report_image)
                            .into(binding.cocktailImage)
                        binding.preparationSteps.text =
                            it.preparationSteps.joinToString("\n") { step -> "• $step" }
                        binding.ingredientsList.text =
                            it.ingredients.joinToString("\n") { ingredient ->
                                // 这里需要根据 ingredient.ingredientId 去查找 IngredientEntity 的名称
                                // 为了简化，暂时直接显示 ID，实际应用中需要通过 ViewModel/Repository 联查
                                "• ${ingredient.amount} ${ingredient.unit} ${ingredient.ingredientId}"
                            }
                        binding.flavorDescription.text = it.flavorDescription?.joinToString(", ")
                        binding.difficulty.text = "难度: ${it.difficulty ?: "未知"}"
                        binding.alcoholStrength.text = "酒精度: ${it.alcoholStrength ?: "未知"}"
                        binding.recommendedGlass.text = "推荐杯具: ${it.recommendedGlass ?: "未知"}"
                        binding.history.text = it.history
                    }
                }
            }
        }

        // 观察 ViewModel 中的收藏状态
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cocktailDetailViewModel.isFavorite.collect { isFavorite ->
                    binding.favoriteButtonDetail.setImageResource(
                        if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                    )
                }
            }
        }

        // 设置收藏按钮点击监听器
        binding.favoriteButtonDetail.setOnClickListener {
            cocktailDetailViewModel.toggleFavoriteStatus()
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
