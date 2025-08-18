package com.example.bartender

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.bartender.adapter.CocktailPagerAdapter
import com.example.bartender.databinding.ActivityMainBinding
import com.example.bartender.model.Cocktail
import com.example.bartender.repository.CocktailRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var repository: CocktailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = CocktailRepository.getInstance(this)

        setSupportActionBar(binding.toolbar)

        setupViewPager()
        setupTabLayout()
        setupFloatingActionButton()
        setupActivityResultLauncher()
    }

    private fun setupActivityResultLauncher() {
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val favoriteStatusChanged = result.data?.getBooleanExtra(CocktailDetailActivity.FAVORITE_STATUS_CHANGED, false) ?: false
                if (favoriteStatusChanged) {
                    // 如果收藏状态改变，刷新当前页面
                    // 这里可以根据需要添加刷新逻辑
                }
            }
        }
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        val pagerAdapter = CocktailPagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    private fun setupTabLayout() {
        tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.all_cocktails)
                1 -> getString(R.string.popular)
                2 -> getString(R.string.favorites)
                else -> null
            }
        }.attach()
    }

    private fun setupFloatingActionButton() {
        binding.floatingActionButton.setOnClickListener {
            showRandomCocktailFromCurrentPage()
        }
    }

    private fun showRandomCocktailFromCurrentPage() {
        val currentPage = viewPager.currentItem
        val cocktails = when (currentPage) {
            0 -> repository.getAllCocktails()
            1 -> repository.getPopularCocktails()
            2 -> repository.getFavoriteCocktails()
            else -> emptyList()
        }

        if (cocktails.isEmpty()) {
            Toast.makeText(this, R.string.no_cocktails_available, Toast.LENGTH_SHORT).show()
            return
        }

        // 随机获取列表中的一条数据
        val randomIndex = Random.nextInt(cocktails.size)
        val cocktail = cocktails[randomIndex]
        showCocktailDialog(cocktail)
    }

    private fun showCocktailDialog(cocktail: Cocktail) {
        val dialog = CocktailDetailDialog(this, cocktail)
        dialog.show()
    }
}