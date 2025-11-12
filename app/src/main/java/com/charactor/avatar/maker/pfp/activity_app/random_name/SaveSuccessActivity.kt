package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.charactor.avatar.maker.pfp.activity_app.main.MainActivity
import com.charactor.avatar.maker.pfp.activity_app.my_nickname.MyNicknameActivity
import com.charactor.avatar.maker.pfp.activity_app.my_nickname.SavedNicknameModel
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivitySaveSuccessBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SaveSuccessActivity : BaseActivity<ActivitySaveSuccessBinding>() {

    companion object {
        private const val PREF_KEY_SAVED_NICKNAMES = "saved_nicknames"
    }

    override fun setViewBinding(): ActivitySaveSuccessBinding {
        return ActivitySaveSuccessBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(com.charactor.avatar.maker.pfp.R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = android.view.View.VISIBLE

        binding.actionBar.tvCenter.text = getString(com.charactor.avatar.maker.pfp.R.string.successful)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(com.charactor.avatar.maker.pfp.R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = android.view.View.VISIBLE

        // Get the saved nickname from intent
        val savedNickname = intent.getStringExtra("SAVED_NICKNAME")

        // Display the nickname
        if (!savedNickname.isNullOrEmpty()) {
            binding.tvSavedNickname.text = savedNickname
            saveNicknameToPrefs(savedNickname)
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
            // Navigate to My Nickname
            startIntentRightToLeft(MyNicknameActivity::class.java)
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // No action bar needed
    }

    private fun saveNicknameToPrefs(nickname: String) {
        val json = sharePreference.preferences.getString(PREF_KEY_SAVED_NICKNAMES, "[]")
        val type = object : TypeToken<MutableList<SavedNicknameModel>>(){}.type
        val nicknames: MutableList<SavedNicknameModel> = Gson().fromJson(json, type) ?: mutableListOf()

        // Add new nickname
        nicknames.add(SavedNicknameModel(nickname = nickname))

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

