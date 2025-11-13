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
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(R.string.random_name)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        setupRecyclerView()
        loadCategories()
    }

    override fun viewListener() {
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
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
            val intent = android.content.Intent(this, CategoryDetailActivity::class.java)
            intent.putExtra("CATEGORY_ID", category.id)
            intent.putExtra("CATEGORY_NAME", category.name)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.rvCategories.adapter = categoryAdapter
    }
    
    private fun loadCategories() {
        val categories = listOf(
            CategoryModel("top", getString(R.string.top), R.drawable.top_img),
            CategoryModel("girl", getString(R.string.girl), R.drawable.girl_img),
            CategoryModel("animals", getString(R.string.animals), R.drawable.animal_img),
            CategoryModel("loved", getString(R.string.loved), R.drawable.love_img),
            CategoryModel("cool", getString(R.string.cool), R.drawable.cool_img),
            CategoryModel("cute", getString(R.string.cute), R.drawable.cute_img),
            CategoryModel("film", getString(R.string.film), R.drawable.film_img),
            CategoryModel("unique", getString(R.string.unique), R.drawable.unique_img),
            CategoryModel("boy", getString(R.string.boy), R.drawable.boy_img),
            CategoryModel("food", getString(R.string.food), R.drawable.food_img),
            CategoryModel("music", getString(R.string.music), R.drawable.music_img),
            CategoryModel("game", getString(R.string.game), R.drawable.game_img)
        )

        categoryAdapter.submitList(categories)
    }
}

