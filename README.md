# 🍹 微醺时刻

## 概览

“微醺时刻”是一款专为调酒爱好者和初学者设计的移动应用程序。它提供了一个全面的鸡尾酒配方数据库，用户可以轻松浏览各种酒品分类、查看详细的制作步骤和所需材料，并收藏自己喜爱的配方。本应用采用离线优先的设计理念，确保用户即使在没有网络连接的情况下也能顺畅地使用核心功能。

## ✨ 主要功能

- **酒品分类浏览：** 清晰展示鸡尾酒、葡萄酒、啤酒、无酒精饮品等多种酒品分类。
  
- **详细配方查看：** 提供每种酒品的详细制作步骤、所需材料、风味描述、难度、酒精强度、推荐杯具及历史背景。
  
- **配方收藏：** 用户可以轻松收藏和管理自己喜爱的酒品配方，并随时离线查看。
  
- **强大的搜索与筛选：** 支持按酒品名称、材料进行模糊搜索，并提供多维度筛选功能（例如按基酒、风味、难度等）。
  
- **离线优先体验：** 所有核心功能（浏览、查看、收藏、搜索、筛选）均可在无网络环境下流畅运行。
  

<!--## 📸 应用截图 -->

<!-- 在这里放置您的应用截图，例如： -->

<!-- -->

<!-- -->

<!-- -->

<!-- -->

## 🛠️ 技术栈

本项目主要基于以下技术和库构建：

- **Kotlin：** 现代化的编程语言，提供空安全、协程等特性。
  
- **Android Jetpack：**
  
  - **Room Persistence Library：** 用于本地数据存储的 ORM 库，简化 SQLite 数据库操作。
    
  - **Android Navigation Component：** 管理应用内导航，提供类型安全的导航方式。
    
  - **ViewModel & LiveData / StateFlow：** 实现 MVVM 架构模式，管理 UI 相关数据和生命周期。
    
  - **Lifecycle：** 处理 Android 组件的生命周期。
    
  - **ViewPager2：** 实现可滑动的多页面布局。
    
- **Hilt (Dagger Hilt)：** 用于依赖注入，简化依赖管理，提高代码可测试性和可维护性。
  
- **Kotlin Coroutines：** 用于异步编程，简化并发操作。
  
- **Glide：** 高性能的图片加载库，用于加载和显示图片。
  
- **Google Material Design：** 提供现代化的 UI 组件和设计指南。
  
- **ViewBinding：** 替代 `findViewById`，提供类型安全和空安全的视图引用。
  
- **AppSearch：** (可选，如果未来集成) 用于设备内高性能全文搜索。
  
- **Gson：** 用于 JSON 序列化和反序列化，特别是 Room TypeConverter。
  

## 🏛️ 架构

本项目遵循 **Clean Architecture** 原则，并结合 **MVVM (Model-View-ViewModel)** 设计模式，以实现高度解耦、可维护、可测试和可扩展的代码库。

- **表现层 (Presentation Layer)：** 包含 Activity、Fragment 和 ViewModel。负责 UI 的显示和用户交互，ViewModel 通过观察数据来更新 UI。
  
- **领域层 (Domain Layer)：** 包含业务逻辑和用例。独立于任何特定的技术实现。
  
- **数据层 (Data Layer)：** 负责处理所有数据源（目前主要是本地 Room 数据库）。通过 Repository 模式提供对应用程序数据的访问，并管理数据的获取、存储和检索。
  

数据流采用 **单向数据流 (Unidirectional Data Flow, UDF)**，确保数据在应用程序的所有层中以单一、可预测的方向流动，简化状态管理和调试。

## 🚀 快速开始

### 前提条件

- Android Studio Arctic Fox (2020.3.1) 或更高版本
  
- JDK 11 或更高版本
  
- Android SDK Platform 34 (或您在 `build.gradle.kts` 中配置的 `compileSdk` 版本)
  

### 克隆仓库

```
git clone https://github.com/your-username/BartenderApp.git
cd BartenderApp
```

### 在 Android Studio 中打开

1. 打开 Android Studio。
  
2. 选择 `File` -> `Open`。
  
3. 导航到您克隆仓库的目录，并选择 `BartenderApp` 文件夹。
  
4. 等待 Gradle 同步完成。
  

### 构建与运行

1. 数据库设置：
  
  本项目默认在首次运行时自动创建并填充数据库。您无需手动放置 app_database.db 文件到 assets 目录。
  
  如果未来您需要预打包数据库，请参考以下步骤：
  
  - 暂时注释掉 `app/src/main/java/com/lcz/bartender/di/AppModule.kt` 中 `provideDatabase` 方法里的 `.createFromAsset("app_database.db")` 这一行。
    
  - 运行应用一次，让 Room 创建一个空的数据库。
    
  - 使用 Android Studio 的 **Device File Explorer** (通常在右侧工具栏) 导航到 `data/data/com.lcz.bartender/databases/` 路径。
    
  - 找到名为 `offline_bartender_app_database` 的文件，右键点击并选择 `Save As...`，将其保存到您的电脑。
    
  - 将保存的文件重命名为 `app_database.db`。
    
  - 在您的项目 `app/src/main/` 目录下创建 `assets` 文件夹（如果不存在）。
    
  - 将 `app_database.db` 文件复制粘贴到 `app/src/main/assets/` 目录下。
    
  - 取消注释 `AppModule.kt` 中 `.createFromAsset("app_database.db")` 这一行。
    
  - 同步 Gradle。
    
2. 同步 Gradle：
  
  在 Android Studio 中，点击工具栏中的 Sync Project with Gradle Files 按钮（通常是一个大象图标）。
  
3. 运行应用：
  
  连接您的 Android 设备或启动模拟器，然后点击 Android Studio 工具栏中的 Run 按钮（绿色播放图标）。
  

## 🤝 贡献

欢迎任何形式的贡献！如果您有任何建议、功能请求或 Bug 报告，请随时提交 Issue 或 Pull Request。

## 📄 许可证

本项目采用 MIT 许可证。详见 [LICENSE](https://www.google.com/search?q=LICENSE "null") 文件。
