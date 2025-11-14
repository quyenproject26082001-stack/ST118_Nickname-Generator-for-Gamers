package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityMyNameBinding

class MyNameActivity : BaseActivity<ActivityMyNameBinding>() {

    override fun setViewBinding(): ActivityMyNameBinding {
        return ActivityMyNameBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.charactor.avatar.maker.pfp.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(R.string.my_name)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        // Initially hide cancel button
        binding.cancelText.visibility = View.GONE
    }

    override fun viewListener() {
        // Back button
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            onBackPressed()
        }

        // Cancel text button - clear input
        binding.cancelText.setOnSingleClick {
            binding.edtInputName.text?.clear()
        }

        // Text watcher to show/hide cancel button
        binding.edtInputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.cancelText.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Generate button
        binding.btnGenerate.setOnSingleClick {
            val inputName = binding.edtInputName.text.toString().trim()
            if (inputName.isEmpty()) {
                Toast.makeText(this, getString(R.string.please_input_your_name), Toast.LENGTH_SHORT).show()
                return@setOnSingleClick
            }

            // Navigate to Nickname list screen
            startIntentRightToLeft<String>(NicknameActivity::class.java, "INPUT_NAME", inputName)
        }

        // Custom nickname button
        binding.btnCustomNickname.setOnSingleClick {
            val inputName = binding.edtInputName.text.toString().trim()
            if (inputName.isEmpty()) {
                Toast.makeText(this, getString(R.string.please_input_your_name), Toast.LENGTH_SHORT).show()
                return@setOnSingleClick
            }

            // Navigate to Customize screen
            startIntentRightToLeft<String>(CustomizeNicknameActivity::class.java, "INPUT_NAME", inputName)
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // Action bar already initialized in initView
    }
}

