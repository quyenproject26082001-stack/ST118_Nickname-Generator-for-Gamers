package com.nicknamecreator.nicknamegenerator.namemaker.activity_app

import android.view.LayoutInflater
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.gone
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.handleBackLeftToRight
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.policy
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.rateApp
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.select
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.shareApp
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.startIntentRightToLeft
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.visible
import com.nicknamecreator.nicknamegenerator.namemaker.core.utils.key.IntentKey
import com.nicknamecreator.nicknamegenerator.namemaker.core.utils.state.RateState
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ActivitySettingsBinding
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.language.LanguageActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.strings
import kotlin.jvm.java

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    override fun setViewBinding(): ActivitySettingsBinding {
        return ActivitySettingsBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        initRate()
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarLeft.setOnSingleClick { handleBackLeftToRight() }
            btnLang.setOnSingleClick { startIntentRightToLeft(LanguageActivity::class.java, IntentKey.INTENT_KEY) }
            btnShare.setOnSingleClick(1500) { shareApp() }
            btnRate.setOnSingleClick {
                rateApp(sharePreference) { state ->
                    if (state != RateState.CANCEL) {
                        binding.btnRate.gone()
                    }
                }
            }
            btnPolicy.setOnSingleClick(1500) { policy() }
        }
    }

    override fun initText() {
        binding.actionBar.tvCenter.select()
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            tvCenter.text = strings(R.string.settings)
            tvCenter.visible()

            btnActionBarLeft.setImageResource(R.drawable.ic_back)
            btnActionBarLeft.visible()
        }
    }

    private fun initRate() {
        if (sharePreference.getIsRate(this)) {
            binding.btnRate.gone()
        } else {
            binding.btnRate.visible()
        }
    }
}