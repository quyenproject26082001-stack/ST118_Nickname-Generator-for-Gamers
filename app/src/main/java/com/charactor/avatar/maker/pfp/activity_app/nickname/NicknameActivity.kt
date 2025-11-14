package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.activity_app.random_name.SaveSuccessActivity
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityNicknameBinding

class NicknameActivity : BaseActivity<ActivityNicknameBinding>() {

    private lateinit var nicknameAdapter: NicknameAdapter
    private var inputName: String = ""

    override fun setViewBinding(): ActivityNicknameBinding {
        return ActivityNicknameBinding.inflate(layoutInflater)
    }

    override fun initView() {
        // Get input name from intent
        inputName = intent.getStringExtra("INPUT_NAME") ?: ""

        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.charactor.avatar.maker.pfp.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(R.string.nickname)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        // Set input name
        binding.edtInputName.setText(inputName)
        
        // Setup RecyclerView
        nicknameAdapter = NicknameAdapter { nickname ->
            // Handle save click - Navigate to SaveSuccessActivity
            startIntentRightToLeft(SaveSuccessActivity::class.java, "SAVED_NICKNAME", nickname.text)
        }
        
        binding.rvNicknames.apply {
            layoutManager = LinearLayoutManager(this@NicknameActivity)
            adapter = nicknameAdapter
        }
        
        // Generate nicknames with different styles
        generateNicknames()
    }

    override fun viewListener() {
        // Back button
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            onBackPressed()
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // Action bar already initialized in initView
    }

    private fun generateNicknames() {
        val nicknames = mutableListOf<NicknameModel>()

        // Use FULL emoji collection from CustomizeNicknameActivity (200+ emojis)
        // Randomly select 8 emojis
        val randomEmojis = SymbolConstants.getRandomEmojis(8)

        // Generate nicknames with random emojis
        randomEmojis.forEach { emoji ->
            val nickname = "$emoji $inputName $emoji"
            nicknames.add(NicknameModel(nickname))
        }

        // Use FULL styles collection from CustomizeNicknameActivity (100+ styles)
        // Randomly select 12 Unicode styles to make total 20 items
        val randomStyles = StyleConstants.ALL_STYLES.shuffled().take(12)

        // Generate styled nicknames
        randomStyles.forEach { styleType ->
            val styledText = CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(inputName, styleType)
            nicknames.add(NicknameModel(styledText))
        }

        // Submit list to adapter
        nicknameAdapter.submitList(nicknames)
    }
}

