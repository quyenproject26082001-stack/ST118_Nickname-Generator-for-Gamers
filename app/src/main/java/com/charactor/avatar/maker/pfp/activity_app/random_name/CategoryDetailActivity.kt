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

        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.charactor.avatar.maker.pfp.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = categoryName
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        setupRecyclerView()
        loadNicknames()
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
        nicknameAdapter = RandomNicknameAdapter { nickname ->
            // Save nickname and navigate to success screen
            saveNickname(nickname)
            // Pass the styled nickname to SaveSuccessActivity
            startIntentRightToLeft(SaveSuccessActivity::class.java, "SAVED_NICKNAME", nickname.styledNickname)
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

