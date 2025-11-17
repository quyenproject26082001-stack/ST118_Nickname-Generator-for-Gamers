package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.main.MainActivity
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.my_nickname.MyNicknameActivity
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.my_nickname.SavedNicknameModel
import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.StyleType
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseActivity
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.startIntentRightToLeft
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ActivitySaveSuccessBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveSuccessActivity : BaseActivity<ActivitySaveSuccessBinding>() {

    companion object {
        private const val PREF_KEY_SAVED_NICKNAMES = "saved_nicknames"
    }

    override fun setViewBinding(): ActivitySaveSuccessBinding {
        return ActivitySaveSuccessBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.nicknamecreator.nicknamegenerator.namemaker.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(com.nicknamecreator.nicknamegenerator.namemaker.R.string.successful)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.nicknamecreator.nicknamegenerator.namemaker.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        // Get the saved nickname and metadata from intent
        val savedNickname = intent.getStringExtra("SAVED_NICKNAME")
        val originalText = intent.getStringExtra("ORIGINAL_TEXT")
        val leftSymbol = intent.getStringExtra("LEFT_SYMBOL")
        val rightSymbol = intent.getStringExtra("RIGHT_SYMBOL")
        val styleTypeName = intent.getStringExtra("STYLE_TYPE")

        // Display the nickname
        if (!savedNickname.isNullOrEmpty()) {
            binding.tvSavedNickname.text = savedNickname
            binding.tvSavedNickname.isSelected = true
            lifecycleScope.launch {
                saveNicknameToPrefs(savedNickname, originalText, leftSymbol, rightSymbol, styleTypeName)
            }
        }

        // Start success animation
        binding.iconSuccess.post {
            startSuccessAnimation()
        }
    }

    override fun viewListener() {
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            onBackPressed()
        }

        binding.btnHome.setOnSingleClick {
            // Navigate to Home
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        binding.btnMyNickname.setOnSingleClick {
            // Navigate to My Nickname with flag to return to home on back
            startIntentRightToLeft(MyNicknameActivity::class.java, "FROM_SUCCESS", true)
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // No action bar needed
    }

    private suspend fun saveNicknameToPrefs(
        nickname: String,
        originalText: String?,
        leftSymbol: String?,
        rightSymbol: String?,
        styleTypeName: String?
    ) = withContext(Dispatchers.IO) {
        val json = sharePreference.preferences.getString(PREF_KEY_SAVED_NICKNAMES, "[]")
        val type = object : TypeToken<MutableList<SavedNicknameModel>>(){}.type
        val nicknames: MutableList<SavedNicknameModel> = Gson().fromJson(json, type) ?: mutableListOf()

        // Check if nickname already exists
        val isDuplicate = nicknames.any { it.nickname == nickname }
        if (isDuplicate) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@SaveSuccessActivity,
                    getString(R.string.nickname_already_exists),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return@withContext
        }

        // Parse StyleType from name
        val styleType = try {
            if (styleTypeName != null) StyleType.valueOf(styleTypeName) else null
        } catch (e: Exception) {
            null
        }

        // Add new nickname with metadata
        nicknames.add(0,
            SavedNicknameModel(
                nickname = nickname,
                originalText = originalText,
                leftSymbol = leftSymbol,
                rightSymbol = rightSymbol,
                styleType = styleType
            )
        )

        // Save back to preferences
        val newJson = Gson().toJson(nicknames)
        sharePreference.preferences.edit()
            .putString(PREF_KEY_SAVED_NICKNAMES, newJson)
            .apply()
    }

    private fun startSuccessAnimation() {
        val handler = Handler(Looper.getMainLooper())

        // Set initial state (invisible)
        binding.ellipse4.alpha = 0f
        binding.ellipse4.scaleX = 0f
        binding.ellipse4.scaleY = 0f

        binding.ellipse3.alpha = 0f
        binding.ellipse3.scaleX = 0f
        binding.ellipse3.scaleY = 0f

        binding.ellipse2.alpha = 0f
        binding.ellipse2.scaleX = 0f
        binding.ellipse2.scaleY = 0f

        binding.ellipse1.alpha = 0f
        binding.ellipse1.scaleX = 0f
        binding.ellipse1.scaleY = 0f

        binding.icSuccess.alpha = 0f
        binding.icSuccess.scaleX = 0f
        binding.icSuccess.scaleY = 0f

        // Animate ellipses from center to outer (ripple effect)
        handler.postDelayed({
            binding.ellipse1.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(400)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }, 200)

        handler.postDelayed({
            binding.ellipse2.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(400)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }, 300)

        handler.postDelayed({
            binding.ellipse3.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(400)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }, 400)

        handler.postDelayed({
            binding.ellipse4.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(400)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }, 500)

        // Animate center icon with bounce + shake
        handler.postDelayed({
            binding.icSuccess.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(300)
                .withEndAction {
                    // Bounce
                    binding.icSuccess.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(200)
                        .setInterpolator(android.view.animation.OvershootInterpolator())
                        .withEndAction {
                            binding.icSuccess.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .withEndAction {
                                    // Shake
                                    shakeIcon()
                                }
                                .start()
                        }
                        .start()
                }
                .start()
        }, 600)
    }

    private fun shakeIcon() {
        binding.icSuccess.animate()
            .rotation(-15f)
            .setDuration(100)
            .withEndAction {
                binding.icSuccess.animate()
                    .rotation(15f)
                    .setDuration(100)
                    .withEndAction {
                        binding.icSuccess.animate()
                            .rotation(-10f)
                            .setDuration(100)
                            .withEndAction {
                                binding.icSuccess.animate()
                                    .rotation(10f)
                                    .setDuration(100)
                                    .withEndAction {
                                        binding.icSuccess.animate()
                                            .rotation(0f)
                                            .setDuration(100)
                                            .start()
                                    }
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()
    }
}

