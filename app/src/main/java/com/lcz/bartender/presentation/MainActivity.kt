
package com.lcz.bartender.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 ViewBinding 绑定布局
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取 NavController
        // 注意：这里使用 findNavController(R.id.nav_host_fragment_activity_main)
        // 因为 NavHostFragment 是 FragmentContainerView 的 name 属性，其 ID 是 nav_host_fragment_activity_main
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as androidx.navigation.fragment.NavHostFragment
        navController = navHostFragment.navController

        // 将 BottomNavigationView 与 NavController 关联
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
