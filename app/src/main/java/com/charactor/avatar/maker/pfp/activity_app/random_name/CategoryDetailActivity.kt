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
        categoryName = intent.getStringExtra("CATEGORY_NAME") ?: categoryId.replaceFirstChar { it.uppercase() }

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
            // Save nickname and navigate to success screen with metadata
            saveNickname(nickname)
            // Pass the styled nickname and metadata to SaveSuccessActivity
            val intent = android.content.Intent(this@CategoryDetailActivity, SaveSuccessActivity::class.java)
            intent.putExtra("SAVED_NICKNAME", nickname.styledNickname)
            intent.putExtra("ORIGINAL_TEXT", nickname.nickname) // Original text without style
            intent.putExtra("LEFT_SYMBOL", nickname.leftSymbol) // Left emoji symbol
            intent.putExtra("RIGHT_SYMBOL", nickname.rightSymbol) // Right emoji symbol
            intent.putExtra("STYLE_TYPE", nickname.styleType?.name) // Style type applied
            startActivity(intent)
            overridePendingTransition(com.charactor.avatar.maker.pfp.R.anim.slide_in_right, com.charactor.avatar.maker.pfp.R.anim.slide_out_left)
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

