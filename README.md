# 调酒师应用

这是一个基于Kotlin开发的Android调酒应用，帮助用户浏览、搜索和收藏各种鸡尾酒配方。

## 功能特点

- 浏览多种鸡尾酒配方
- 查看详细的原料和制作步骤
- 按难度和类型筛选鸡尾酒
- 搜索特定的鸡尾酒
- 收藏喜欢的鸡尾酒配方
- 分享鸡尾酒配方给朋友

## 技术栈

- Kotlin语言
- Android Jetpack组件
  - ViewModel
  - LiveData
  - ViewBinding
  - RecyclerView
  - ViewPager2
- Material Design组件

## 项目结构

- `model/`: 数据模型类
- `repository/`: 数据仓库类
- `adapter/`: RecyclerView适配器类
- `fragment/`: Fragment类
- 主要Activity: MainActivity和CocktailDetailActivity

## 如何使用

1. 在Android Studio中打开项目
2. 构建并运行应用
3. 浏览鸡尾酒列表，点击查看详情
4. 使用搜索功能查找特定鸡尾酒
5. 点击收藏按钮收藏喜欢的鸡尾酒

## 未来计划

- 添加更多鸡尾酒配方
- 实现用户自定义鸡尾酒功能
- 添加更多筛选选项
- 集成在线数据库
- 添加用户评分和评论功能