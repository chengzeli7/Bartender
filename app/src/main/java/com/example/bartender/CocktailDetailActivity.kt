package com.example.bartender

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bartender.adapter.IngredientAdapter
import com.example.bartender.databinding.ActivityCocktailDetailBinding
import com.example.bartender.model.Cocktail
import com.example.bartender.model.Difficulty
import com.bumptech.glide.Glide
import com.example.bartender.repository.CocktailRepository

class CocktailDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailDetailBinding
    private lateinit var repository: CocktailRepository
    private var cocktailId: Int = -1
    private var cocktail: Cocktail? = null
    private var favoriteStatusChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = CocktailRepository.getInstance(this)

        cocktailId = intent.getIntExtra(EXTRA_COCKTAIL_ID, -1)
        if (cocktailId == -1) {
            finish()
            return
        }

        setupToolbar()
        loadCocktailDetails()
        setupFavoriteButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadCocktailDetails() {
        cocktail = repository.getCocktailById(cocktailId)
        cocktail?.let { cocktail ->
            binding.collapsingToolbar.title = cocktail.name

            // 使用Glide加载图片
            Glide.with(this)
                .load(cocktail.imageUrl)
                .placeholder(R.drawable.ic_favorite)
                .into(binding.cocktailImage)

            // 设置难度标签
            val difficultyText = when (cocktail.difficulty) {
                Difficulty.EASY -> getString(R.string.easy)
                Difficulty.MEDIUM -> getString(R.string.medium)
                Difficulty.HARD -> getString(R.string.hard)
            }
            binding.difficultyChip.text = difficultyText

            // 设置原料列表
            val ingredientAdapter = IngredientAdapter(cocktail.ingredients)
            binding.ingredientsRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@CocktailDetailActivity)
                adapter = ingredientAdapter
            }

            // 设置制作步骤
            binding.instructionsText.text = cocktail.instructions.mapIndexed { index, instruction ->
                "${index + 1}. $instruction"
            }.joinToString("\n")

            updateFavoriteButton(cocktail.isFavorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.favoriteFab.setOnClickListener {
            cocktail?.let {
                repository.toggleFavorite(it.id)
                it.isFavorite = !it.isFavorite
                updateFavoriteButton(it.isFavorite)
                favoriteStatusChanged = true
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteFab.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.favoriteFab.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun finish() {
        val resultIntent = Intent()
        resultIntent.putExtra(FAVORITE_STATUS_CHANGED, favoriteStatusChanged)
        setResult(RESULT_OK, resultIntent)
        super.finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_COCKTAIL_ID = "extra_cocktail_id"
        const val FAVORITE_STATUS_CHANGED = "favorite_status_changed"
    }
}