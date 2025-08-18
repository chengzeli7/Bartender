// CocktailDetailDialog.kt
package com.example.bartender

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bartender.databinding.DialogCocktailBinding
import com.example.bartender.model.Cocktail
import com.example.bartender.model.Difficulty

class CocktailDetailDialog(
    context: Context,
    private val cocktail: Cocktail
) : BaseDialog<DialogCocktailBinding>(context) {

    override fun getViewBinding(inflater: LayoutInflater): DialogCocktailBinding {
        return DialogCocktailBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        // 在这里移除默认的白色背景
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 设置点击外部是否可以取消，根据你的需要调整
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT)

        // 绑定视图和数据
        Glide.with(context)
            .load(cocktail.imageUrl)
            .placeholder(R.drawable.ic_cocktail)
            .into(binding.cocktailImage)

        binding.cocktailName.text = cocktail.name
        binding.cocktailDescription.text = cocktail.description

        val difficultyText = when (cocktail.difficulty) {
            Difficulty.EASY -> context.getString(R.string.easy)
            Difficulty.MEDIUM -> context.getString(R.string.medium)
            Difficulty.HARD -> context.getString(R.string.hard)
        }
        binding.cocktailDifficulty.text = "难度: $difficultyText"

        val ingredientsText = cocktail.ingredients.joinToString("\n") {
            "${it.name}: ${it.amount}"
        }
        binding.cocktailIngredients.text = ingredientsText
        binding.cocktailDialog.setOnSingleClickListener {
            val intent = Intent(context, CocktailDetailActivity::class.java)
            intent.putExtra(CocktailDetailActivity.EXTRA_COCKTAIL_ID, cocktail.id)
            context.startActivity(intent)
            dismiss()
        }
    }

    override fun initData() {
        // 在这里处理数据加载逻辑
    }
}