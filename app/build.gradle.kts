plugins {
    // 启用 Android 应用程序插件
    id("com.android.application")
    // 启用 Kotlin Android 插件
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    // 启用 Hilt Android 插件
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin") // 不需要版本号，因为它在项目级别定义
}

android {
    namespace = "com.lcz.bartender"
    compileSdk = 36 // 从 35 更新到 36

    defaultConfig {
        applicationId = "com.lcz.bartender"
        minSdk = 24
        targetSdk = 36 // 从 35 更新到 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    // 启用 ViewBinding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.9.2")

    // Views/Fragments Integration
    implementation("androidx.navigation:navigation-fragment:2.9.2")
    implementation("androidx.navigation:navigation-ui:2.9.2")

    // Kotlin 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Room 数据库
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // Room Kotlin 协程支持
    implementation("androidx.room:room-ktx:2.6.1")

    // Hilt 依赖注入
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Glide 图片加载库
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // AppSearch (用于离线搜索)
    implementation("androidx.appsearch:appsearch:1.1.0")
    // 如果需要AppSearch的Room集成
    implementation("androidx.appsearch:appsearch-local-storage:1.1.0")

    implementation("com.google.code.gson:gson:2.13.1")

    // 标题栏框架：https://github.com/getActivity/TitleBar
    implementation("com.github.getActivity:TitleBar:10.5")
}

// 允许 Hilt 生成代码
kapt {
    correctErrorTypes = true
}
