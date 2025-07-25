package com.lcz.bartender

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 应用程序类，使用 @HiltAndroidApp 注解启用 Hilt。
 * 这是 Hilt 依赖注入的入口点。
 */
@HiltAndroidApp
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 可以在这里进行其他应用程序级别的初始化
    }
}