package com.example.bartender.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bartender.R
import com.example.bartender.databinding.ItemCocktailBinding
import com.example.bartender.model.Cocktail
import com.example.bartender.model.Difficulty

class CocktailAdapter(private val onItemClick: (Cocktail) -> Unit) :
    ListAdapter<Cocktail, CocktailAdapter.CocktailViewHolder>(CocktailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(cocktail: Cocktail) {
            binding.apply {
                cocktailName.text = cocktail.name
                cocktailDescription.text = cocktail.description

                // 使用 Glide 加载图片
                Glide.with(root.context)
                    .load(cocktail.imageUrl)
                    .placeholder(R.drawable.ic_cocktail)
                    .into(cocktailImage)

                // 设置难度标签
                val difficultyText = when (cocktail.difficulty) {
                    Difficulty.EASY -> binding.root.context.getString(R.string.easy)
                    Difficulty.MEDIUM -> binding.root.context.getString(R.string.medium)
                    Difficulty.HARD -> binding.root.context.getString(R.string.hard)
                }
                difficultyChip.text = difficultyText
            }
        }
    }

    class CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }
}