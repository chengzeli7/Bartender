package com.lcz.bartender.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lcz.bartender.R
import com.lcz.bartender.databinding.ActivityMainBinding
import com.lcz.bartender.presentation.cocktaillist.CocktailListFragment
import com.lcz.bartender.presentation.favorites.FavoritesFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.lcz.bartender.base.BaseActivity
import com.lcz.bartender.databinding.FragmentCocktailDetailBinding
import com.lcz.bartender.presentation.cocktaildetail.CocktailDetailViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

/**
 * 显示单个鸡尾酒详细信息的 Activity。
 * 使用 @AndroidEntryPoint 注解启用 Hilt 对此 Activity 的依赖注入。
 */
@AndroidEntryPoint
class CocktailDetailActivity : BaseActivity<FragmentCocktailDetailBinding>() {

    // Change: Pass cocktailId to the ViewModel's constructor
    private val cocktailDetailViewModel: CocktailDetailViewModel by viewModels()


    override fun initView() {
        // 从 Intent extras 中检索 cocktailId
        val cocktailId = intent.getStringExtra(EXTRA_COCKTAIL_ID)
        if (cocktailId == null) {
            // 处理未提供 cocktailId 的情况，例如，结束 Activity
            finish()
            return
        }
    }

    override fun initData() {
        // 观察 ViewModel 中的鸡尾酒详情数据
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cocktailDetailViewModel.cocktail.collect { cocktail ->
                    cocktail?.let {
                        // 填充 UI 元素
                        binding.cocktailName.text = it.name
                        binding.titleBar.setTitle(it.name)
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
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cocktailDetailViewModel.isFavorite.collect { isFavorite ->
                    binding.favoriteButtonDetail.setImageResource(
                        if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                    )
                }
            }
        }
    }

    override fun initListeners() {
        // 设置收藏按钮点击监听器
        binding.favoriteButtonDetail.setOnClickListener {
            cocktailDetailViewModel.toggleFavoriteStatus()
        }
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                finish()
            }
        })
    }

    companion object {
        private const val EXTRA_COCKTAIL_ID = "cocktailId" // 定义 extra 键的常量

        // 修改 actionStart 以接受 cocktailId
        fun actionStart(context: Context, cocktailId: String) {
            val intent = Intent(context, CocktailDetailActivity::class.java).apply {
                putExtra(EXTRA_COCKTAIL_ID, cocktailId) // 将 cocktailId 放入 Intent 中
            }
            context.startActivity(intent)
        }
    }
}