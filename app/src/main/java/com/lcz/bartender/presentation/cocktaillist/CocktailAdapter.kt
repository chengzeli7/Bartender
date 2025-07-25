package com.lcz.bartender.presentation.cocktaillist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lcz.bartender.R
import com.lcz.bartender.databinding.ItemCocktailBinding
import com.lcz.bartender.presentation.CocktailWithFavoriteStatus

/**
 * 用于显示鸡尾酒列表的 RecyclerView 适配器。
 * 使用 ListAdapter 提高性能，因为它会自动处理列表差异计算。
 * @param onItemClick 点击鸡尾酒项时的回调函数，传入鸡尾酒 ID。
 * @param onFavoriteClick 点击收藏按钮时的回调函数，传入鸡尾酒 ID 和当前收藏状态。
 */
class CocktailAdapter(
    private val onItemClick: (String) -> Unit,
    private val onFavoriteClick: (String, Boolean) -> Unit
) : ListAdapter<CocktailWithFavoriteStatus, CocktailAdapter.CocktailViewHolder>(CocktailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktailWithStatus = getItem(position)
        holder.bind(cocktailWithStatus, onItemClick, onFavoriteClick)
    }

    /**
     * ViewHolder 类，用于绑定单个鸡尾酒项的数据和视图。
     */
    class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cocktailWithStatus: CocktailWithFavoriteStatus,
            onItemClick: (String) -> Unit,
            onFavoriteClick: (String, Boolean) -> Unit
        ) {
            val cocktail = cocktailWithStatus.cocktail
            val isFavorite = cocktailWithStatus.isFavorite

            // 设置鸡尾酒名称
            binding.cocktailName.text = cocktail.name
            // 设置简要描述（例如，主要基酒或风味特点）
            binding.cocktailDescription.text = cocktail.flavorDescription?.joinToString(", ") ?: ""

            // 使用 Glide 加载图片
            Glide.with(binding.cocktailImage.context)
                .load(cocktail.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.cocktailImage)

            // 设置收藏按钮图标
            binding.favoriteButton.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )

            // 设置项点击监听器
            binding.root.setOnClickListener {
                onItemClick(cocktail.id)
            }

            // 设置收藏按钮点击监听器
            binding.favoriteButton.setOnClickListener {
                onFavoriteClick(cocktail.id, isFavorite)
            }
        }
    }

    /**
     * DiffUtil.ItemCallback 实现，用于计算列表项的差异。
     */
    private class CocktailDiffCallback : DiffUtil.ItemCallback<CocktailWithFavoriteStatus>() {
        override fun areItemsTheSame(oldItem: CocktailWithFavoriteStatus, newItem: CocktailWithFavoriteStatus): Boolean {
            return oldItem.cocktail.id == newItem.cocktail.id
        }

        override fun areContentsTheSame(oldItem: CocktailWithFavoriteStatus, newItem: CocktailWithFavoriteStatus): Boolean {
            return oldItem == newItem
        }
    }
}
