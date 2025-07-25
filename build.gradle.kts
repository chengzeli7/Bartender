// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // 启用 Android 应用程序插件，设置为兼容您Android Studio的版本
    id("com.android.application") version "8.9.1" apply false // 确保与Android Studio兼容
    // 启用 Kotlin Android 插件
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    alias(libs.plugins.kotlin.compose) apply false

    // 启用 Hilt Android 插件
    id("com.google.dagger.hilt.android") version "2.48" apply false

    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}
