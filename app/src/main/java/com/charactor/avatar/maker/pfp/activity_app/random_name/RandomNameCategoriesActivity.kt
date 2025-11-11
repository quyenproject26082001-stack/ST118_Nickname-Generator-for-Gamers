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
            CategoryModel("top", "Top", R.drawable.ic_random),
            CategoryModel("girl", "Girl", R.drawable.ic_random),
            CategoryModel("animals", "Animals", R.drawable.ic_random),
            CategoryModel("loved", "Loved", R.drawable.ic_random),
            CategoryModel("cool", "Cool", R.drawable.ic_random),
            CategoryModel("cute", "Cute", R.drawable.ic_random),
            CategoryModel("film", "Film", R.drawable.ic_random),
            CategoryModel("unique", "Unique", R.drawable.ic_random),
            CategoryModel("boy", "Boy", R.drawable.ic_random),
            CategoryModel("food", "Food", R.drawable.ic_random),
            CategoryModel("music", "Music", R.drawable.ic_random),
            CategoryModel("game", "Game", R.drawable.ic_random)
        )
        
        categoryAdapter.submitList(categories)
    }
}

