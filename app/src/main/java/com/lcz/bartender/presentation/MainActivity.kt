package com.lcz.bartender.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
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

/**
 * 主 Activity，应用程序的入口点。
 * 负责设置底部导航栏和导航组件。
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 ViewBinding 绑定布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 设置状态栏图标为深色（黑色）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6.0 (API 23) 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 (API 30) 及以上
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else { // Android 6.0 (API 23) 到 Android 10 (API 29)
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        // 设置 ViewPager2 适配器
        val pagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        // 禁用 ViewPager2 的用户滑动，通过底部导航控制切换
        binding.viewPager.isUserInputEnabled = false

        // 设置底部导航栏的选中监听器
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_categories -> {
                    // 切换到鸡尾酒列表页面 (现在作为左侧Tab)
                    binding.viewPager.currentItem = 0
                    true
                }

                R.id.navigation_favorites -> {
                    // 切换到收藏页面
                    binding.viewPager.currentItem = 1
                    true
                }

                else -> false
            }
        }

        // 确保 ViewPager2 和 BottomNavigationView 保持同步
        binding.viewPager.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })
    }

    /**
     * ViewPager2 的适配器，用于管理不同 Fragment 的页面。
     */
    private class MainPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2 // 两个 Tab: 鸡尾酒列表和收藏

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    // 第一个 Tab 是鸡尾酒列表页面
                    // 需要传递一个默认的 categoryId，因为 CocktailListFragment 需要它
                    val bundle =
                        Bundle().apply { putString("categoryId", "cocktail") } // 传递空字符串作为默认 categoryId
                    val cocktailListFragment = CocktailListFragment()
                    cocktailListFragment.arguments = bundle
                    cocktailListFragment
                }

                1 -> FavoritesFragment() // 第二个 Tab 是收藏页面
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
    }
}