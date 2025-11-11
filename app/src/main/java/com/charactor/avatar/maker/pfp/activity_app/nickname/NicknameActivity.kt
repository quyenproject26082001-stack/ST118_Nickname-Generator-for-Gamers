package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
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
        
        // Setup action bar title
        binding.actionBar.tvActionBarTitle.text = "Nickname"
        binding.actionBar.tvActionBarTitle.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        
        // Set input name
        binding.edtInputName.setText(inputName)
        
        // Setup RecyclerView
        nicknameAdapter = NicknameAdapter { nickname ->
            // Handle save click
            Toast.makeText(this, "Saved: ${nickname.text}", Toast.LENGTH_SHORT).show()
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
        
        // Generate different styled nicknames
        val symbols = listOf(
            "★" to "★",
            "♥" to "♥",
            "◆" to "◆",
            "●" to "●",
            "▲" to "▲",
            "✦" to "✦",
            "❖" to "❖",
            "◈" to "◈"
        )
        
        val fonts = listOf(
            "roboto_regular",
            "roboto_bold",
            "roboto_italic",
            "roboto_medium",
            "sigmar_regular",
            "londrina_solid_regular",
            "montserrat_bold",
            "montserrat_italic",
            "toruksc_regular"
        )
        
        // Generate nicknames with symbols and different fonts
        symbols.forEachIndexed { index, (leftSymbol, rightSymbol) ->
            val font = fonts[index % fonts.size]
            val nickname = "$leftSymbol $inputName $rightSymbol"
            nicknames.add(NicknameModel(nickname, font))
        }
        
        // Add plain nicknames with different fonts
        fonts.forEach { font ->
            nicknames.add(NicknameModel(inputName, font))
        }
        
        // Submit list to adapter
        nicknameAdapter.submitList(nicknames)
    }
}

