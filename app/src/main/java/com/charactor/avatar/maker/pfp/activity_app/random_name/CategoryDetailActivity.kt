package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityCategoryDetailBinding

class CategoryDetailActivity : BaseActivity<ActivityCategoryDetailBinding>() {
    
    private lateinit var nicknameAdapter: RandomNicknameAdapter
    private var categoryId: String = ""
    private var categoryName: String = ""
    
    override fun setViewBinding(): ActivityCategoryDetailBinding {
        return ActivityCategoryDetailBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Get category from intent
        categoryId = intent.getStringExtra("CATEGORY_ID") ?: "top"
        categoryName = categoryId.replaceFirstChar { it.uppercase() }
        
        binding.tvCategoryTitle.text = categoryName
        
        setupRecyclerView()
        loadNicknames()
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
        nicknameAdapter = RandomNicknameAdapter { nickname ->
            // Save nickname and navigate to success screen
            saveNickname(nickname)
            startIntentRightToLeft(SaveSuccessActivity::class.java, "SAVED_NICKNAME", nickname.nickname)
        }
        
        binding.rvNicknames.apply {
            layoutManager = LinearLayoutManager(this@CategoryDetailActivity)
            adapter = nicknameAdapter
        }
    }
    
    private fun loadNicknames() {
        val nicknames = NicknameDataSource.getRandomNicknames(categoryId, 20)
        nicknameAdapter.submitList(nicknames)
    }
    
    private fun saveNickname(nickname: RandomNicknameModel) {
        // TODO: Save to database or shared preferences
        // For now, just log it
        android.util.Log.d("CategoryDetail", "Saved nickname: ${nickname.nickname}")
    }
}

