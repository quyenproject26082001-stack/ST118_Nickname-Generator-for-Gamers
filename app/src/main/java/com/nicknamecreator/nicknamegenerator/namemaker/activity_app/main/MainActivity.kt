package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.rateApp
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.select
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.startIntentRightToLeft
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.visible
import com.nicknamecreator.nicknamegenerator.namemaker.core.helper.LanguageHelper
import com.nicknamecreator.nicknamegenerator.namemaker.core.helper.MediaHelper
import com.nicknamecreator.nicknamegenerator.namemaker.core.utils.key.ValueKey
import com.nicknamecreator.nicknamegenerator.namemaker.core.utils.state.RateState
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ActivityHomeBinding
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.SettingsActivity
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.MyNameActivity
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name.RandomNameCategoriesActivity
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.my_nickname.MyNicknameActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.gone
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.strings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityHomeBinding>() {

    override fun setViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        deleteTempFolder()
        setupTextStroke()
    }

    private fun setupTextStroke() {
        val strokeColor = "#3E001C".toColorInt()
        val strokeWidth = 1f * resources.displayMetrics.density // Convert 1dp to pixels

        binding.tvMyName.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        binding.tv1.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        binding.tv2.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        // Enable marquee effect for long text
        binding.tvMyName.isSelected = true
        binding.tv1.isSelected = true
        binding.tv2.isSelected = true
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarRight.setOnSingleClick { startIntentRightToLeft(SettingsActivity::class.java) }

            // Random Name button
            btnRandom.setOnSingleClick {
                startIntentRightToLeft(RandomNameCategoriesActivity::class.java)
            }

            // My Name button
            btnMyName.setOnSingleClick {
                startIntentRightToLeft(MyNameActivity::class.java)
            }

            // My Nickname button
            btnMyNickname.setOnSingleClick {
                startIntentRightToLeft(MyNicknameActivity::class.java)
            }
        }
    }

    override fun initText() {
        super.initText()
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            imgHeadTitle.gone()
            btnActionBarRight.setImageResource(R.drawable.ic_settings)
            btnActionBarRight.visible()

        }
    }

    @SuppressLint("MissingSuperCall", "GestureBackNavigation")
    override fun onBackPressed() {
        if (!sharePreference.getIsRate(this) && sharePreference.getCountBack() % 2 != 0) {
            rateApp(sharePreference) { state ->
                when (state) {
                    RateState.LESS3 -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            finishAffinity()
                        }
                    }

                    RateState.GREATER3 -> {
                        // Thoát app sau khi rate
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            finishAffinity()
                        }
                    }
                    RateState.CANCEL -> {
                        lifecycleScope.launch {
                            sharePreference.setCountBack(sharePreference.getCountBack() + 1)
                            withContext(Dispatchers.Main) {
                                delay(1000)
                                finishAffinity()
                            }
                        }
                    }
                }
            }
        } else {
            sharePreference.setCountBack(sharePreference.getCountBack() + 1)
            finishAffinity()
        }
    }

    private fun deleteTempFolder() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dataTemp = MediaHelper.getImageInternal(this@MainActivity, ValueKey.DOWNLOAD_ALBUM_BACKGROUND)
            if (dataTemp.isNotEmpty()) {
                dataTemp.forEach {
                    val file = File(it)
                    file.delete()
                }
            }
        }
    }

    private fun updateText() {
        binding.apply {
            tv1.text = strings(R.string.random_name)
            tv2.text = strings(R.string.My_NickName)
            tvMyName.text = strings(R.string.my_name)

            // Re-enable marquee after text update
            tvMyName.isSelected = true
            tv1.isSelected = true
            tv2.isSelected = true
        }
    }

    override fun onRestart() {
        super.onRestart()
        LanguageHelper.setLocale(this)
        updateText()
    }
}