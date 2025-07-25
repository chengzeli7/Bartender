package com.lcz.bartender.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lcz.bartender.databinding.ItemCategoryBinding

/**
 * 用于显示酒品分类列表的 RecyclerView 适配器。
 * 使用 ListAdapter 提高性能，因为它会自动处理列表差异计算。
 * @param onItemClick 点击分类项时的回调函数，传入分类 ID。
 */
class CategoryAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<CategoryEntity, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // 绑定 item_category.xml 布局
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, onItemClick)
    }

    /**
     * ViewHolder 类，用于绑定单个分类项的数据和视图。
     */
    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryEntity, onItemClick: (String) -> Unit) {
            // 设置分类名称
            binding.categoryName.text = category.name
            // 使用 Glide 加载图片，并设置占位符和错误图片
            Glide.with(binding.categoryImage.context)
                .load(category.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery) // 占位符
                .error(android.R.drawable.ic_menu_report_image) // 错误图片
                .into(binding.categoryImage)

            // 设置点击监听器
            binding.root.setOnClickListener {
                onItemClick(category.id)
            }
        }
    }

    /**
     * DiffUtil.ItemCallback 实现，用于计算列表项的差异，优化 RecyclerView 性能。
     */
    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryEntity>() {
        override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
            return oldItem == newItem
        }
    }
}
