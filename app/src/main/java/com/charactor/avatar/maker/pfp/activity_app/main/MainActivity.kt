package com.charactor.avatar.maker.pfp.activity_app.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.rateApp
import com.charactor.avatar.maker.pfp.core.extensions.select
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.core.extensions.visible
import com.charactor.avatar.maker.pfp.core.helper.LanguageHelper
import com.charactor.avatar.maker.pfp.core.helper.MediaHelper
import com.charactor.avatar.maker.pfp.core.utils.key.ValueKey
import com.charactor.avatar.maker.pfp.core.utils.state.RateState
import com.charactor.avatar.maker.pfp.databinding.ActivityHomeBinding
import com.charactor.avatar.maker.pfp.activity_app.SettingsActivity
import com.charactor.avatar.maker.pfp.activity_app.nickname.MyNameActivity
import com.charactor.avatar.maker.pfp.activity_app.random_name.RandomNameCategoriesActivity
import com.charactor.avatar.maker.pfp.activity_app.my_nickname.MyNicknameActivity
import com.charactor.avatar.maker.pfp.core.extensions.gone
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.strings
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
        if (!sharePreference.getIsRate(this) && sharePreference.getCountBack() % 2 == 0) {
            rateApp(sharePreference) { state ->
                when (state) {
                    RateState.LESS3 -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            exitProcess(0)
                        }
                    }

                    RateState.GREATER3 -> {}
                    RateState.CANCEL -> {
                        lifecycleScope.launch {
                            sharePreference.setCountBack(sharePreference.getCountBack() + 1)
                            withContext(Dispatchers.Main) {
                                delay(1000)
                                exitProcess(0)
                            }
                        }
                    }
                }
            }
        } else {
            exitProcess(0)
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
        }
    }

    override fun onRestart() {
        super.onRestart()
        LanguageHelper.setLocale(this)
        updateText()
    }
}