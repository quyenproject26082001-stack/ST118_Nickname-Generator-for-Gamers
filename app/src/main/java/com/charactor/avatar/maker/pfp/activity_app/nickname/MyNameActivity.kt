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
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.charactor.avatar.maker.pfp.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = "My name"
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE
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
            startIntentRightToLeft(NicknameActivity::class.java, "INPUT_NAME", inputName)
        }

        // Custom nickname button
        binding.btnCustomNickname.setOnSingleClick {
            val inputName = binding.edtInputName.text.toString().trim()
            if (inputName.isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
                return@setOnSingleClick
            }

            // Navigate to Customize screen
            startIntentRightToLeft(CustomizeNicknameActivity::class.java, "INPUT_NAME", inputName)
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // Action bar already initialized in initView
    }
}

