package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.view.LayoutInflater
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityRandomNameCategoriesBinding

class RandomNameCategoriesActivity : BaseActivity<ActivityRandomNameCategoriesBinding>() {
    
    private lateinit var categoryAdapter: CategoryAdapter
    
    override fun setViewBinding(): ActivityRandomNameCategoriesBinding {
        return ActivityRandomNameCategoriesBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        setupRecyclerView()
        loadCategories()
    }

    override fun viewListener() {
        binding.btnBack.setOnSingleClick {
            onBackPressed()
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // No action bar needed
    }
    
    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            // Navigate to category detail
            startIntentRightToLeft(CategoryDetailActivity::class.java, "CATEGORY_ID", category.id)
        }

        binding.rvCategories.adapter = categoryAdapter
    }
    
    private fun loadCategories() {
        val categories = listOf(
            CategoryModel("top", "Top", R.drawable.top_img),
            CategoryModel("girl", "Girl", R.drawable.girl_img),
            CategoryModel("animals", "Animals", R.drawable.animal_img),
            CategoryModel("loved", "Loved", R.drawable.love_img),
            CategoryModel("cool", "Cool", R.drawable.cool_img),
            CategoryModel("cute", "Cute", R.drawable.cute_img),
            CategoryModel("film", "Film", R.drawable.film_img),
            CategoryModel("unique", "Unique", R.drawable.unique_img),
            CategoryModel("boy", "Boy", R.drawable.boy_img),
            CategoryModel("food", "Food", R.drawable.food_img),
            CategoryModel("music", "Music", R.drawable.music_img),
            CategoryModel("game", "Game", R.drawable.game_img)
        )

        categoryAdapter.submitList(categories)
    }
}

