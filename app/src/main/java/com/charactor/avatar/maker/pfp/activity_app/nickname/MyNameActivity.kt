package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.widget.Toast
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityMyNameBinding

class MyNameActivity : BaseActivity<ActivityMyNameBinding>() {

    override fun setViewBinding(): ActivityMyNameBinding {
        return ActivityMyNameBinding.inflate(layoutInflater)
    }

    override fun initView() {
        // Setup action bar title
        binding.actionBar.tvActionBarTitle.text = "My name"
        binding.actionBar.tvActionBarTitle.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
    }

    override fun viewListener() {
        // Back button
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            onBackPressed()
        }

        // Generate button
        binding.btnGenerate.setOnSingleClick {
            val inputName = binding.edtInputName.text.toString().trim()
            if (inputName.isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
                return@setOnSingleClick
            }
            
            // Navigate to Nickname list screen
            startIntentRightToLeft(NicknameActivity::class.java) {
                putExtra("INPUT_NAME", inputName)
            }
        }

        // Custom nickname button
        binding.btnCustomNickname.setOnSingleClick {
            val inputName = binding.edtInputName.text.toString().trim()
            if (inputName.isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
                return@setOnSingleClick
            }
            
            // Navigate to Custom Nickname screen (will be implemented later)
            Toast.makeText(this, "Custom nickname feature coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // Action bar already initialized in initView
    }
}

