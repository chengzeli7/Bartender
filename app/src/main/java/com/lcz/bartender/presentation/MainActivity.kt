package com.lcz.bartender.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.lcz.bartender.R
import com.lcz.bartender.databinding.ActivityMainBinding
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
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        // 获取 NavController
        // 注意：这里使用 findNavController(R.id.nav_host_fragment_activity_main)
        // 因为 NavHostFragment 是 FragmentContainerView 的 name 属性，其 ID 是 nav_host_fragment_activity_main
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as androidx.navigation.fragment.NavHostFragment
        navController = navHostFragment.navController

        // 将 BottomNavigationView 与 NavController 关联
        binding.bottomNavigationView.setupWithNavController(navController)
        //禁止长按toast
        binding.bottomNavigationView.menu.forEach {
            val view = binding.bottomNavigationView.findViewById<View>(it.itemId)
            view.setOnLongClickListener {
                true
            }
        }

        // 添加一个监听器来手动处理底部导航栏的选中状态
        // 尤其是在从深层导航返回时，确保底部导航栏的选中项正确
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_favorites -> {
                    binding.bottomNavigationView.menu.findItem(R.id.navigation_favorites)?.isChecked =
                        true
                }

                else -> {
                    binding.bottomNavigationView.menu.findItem(R.id.navigation_categories)?.isChecked =
                        true
                }
            }
        }


    }
}
