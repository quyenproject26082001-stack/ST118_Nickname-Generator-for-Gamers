package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.content.Intent
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
}

