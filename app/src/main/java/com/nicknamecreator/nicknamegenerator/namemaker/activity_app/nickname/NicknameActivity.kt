package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name.SaveSuccessActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.startIntentRightToLeft
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ActivityNicknameBinding

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
        binding.actionBar.btnActionBarLeft.setImageResource(com.nicknamecreator.nicknamegenerator.namemaker.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(R.string.nickname)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.nicknamecreator.nicknamegenerator.namemaker.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        // Set input name
        binding.edtInputName.setText(inputName)
        
        // Setup RecyclerView
        nicknameAdapter = NicknameAdapter { nickname ->
            // Handle save click - Navigate to SaveSuccessActivity with metadata
            val intent = android.content.Intent(this@NicknameActivity, SaveSuccessActivity::class.java)
            intent.putExtra("SAVED_NICKNAME", nickname.text)
            intent.putExtra("ORIGINAL_TEXT", nickname.originalText)
            intent.putExtra("LEFT_SYMBOL", nickname.leftSymbol)
            intent.putExtra("RIGHT_SYMBOL", nickname.rightSymbol)
            intent.putExtra("STYLE_TYPE", nickname.styleType?.name)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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

        // Generate 20 nicknames with BOTH emoji AND unicode style (like CategoryDetailActivity)
        // Use FULL emoji collection (200+ emojis) and FULL styles collection (180+ styles)
        repeat(20) {
            // Random emoji from SymbolConstants
            val randomEmoji = SymbolConstants.ALL_EMOJIS.random()

            // Random unicode style from StyleConstants
            val randomStyle = StyleConstants.ALL_STYLES.random()

            // Add emoji decoration to text first (like CustomizeNicknameActivity)
            val baseText = "$randomEmoji $inputName $randomEmoji"

            // Then apply unicode style to the entire text including emojis
            val finalNickname = CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(baseText, randomStyle)

            nicknames.add(
                NicknameModel(
                    text = finalNickname,
                    originalText = inputName,
                    leftSymbol = randomEmoji,
                    rightSymbol = randomEmoji,
                    styleType = randomStyle
                )
            )
        }

        // Submit list to adapter
        nicknameAdapter.submitList(nicknames)
    }
}

