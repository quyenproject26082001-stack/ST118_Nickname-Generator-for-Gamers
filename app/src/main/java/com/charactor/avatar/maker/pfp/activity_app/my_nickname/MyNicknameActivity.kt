package com.charactor.avatar.maker.pfp.activity_app.my_nickname

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.activity_app.main.MainActivity
import com.charactor.avatar.maker.pfp.activity_app.nickname.CustomizeNicknameActivity
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityMyNicknameBinding
import com.charactor.avatar.maker.pfp.databinding.DialogConfirmBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyNicknameActivity : BaseActivity<ActivityMyNicknameBinding>() {

    private lateinit var savedNicknameAdapter: SavedNicknameAdapter
    private val savedNicknames = mutableListOf<SavedNicknameModel>()
    private var fromSuccess: Boolean = false

    companion object {
        private const val PREF_KEY_SAVED_NICKNAMES = "saved_nicknames"
    }
    
    override fun setViewBinding(): ActivityMyNicknameBinding {
        return ActivityMyNicknameBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Get flag from intent
        fromSuccess = intent.getBooleanExtra("FROM_SUCCESS", false)

        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = View.VISIBLE

        binding.actionBar.tvCenter.text = getString(R.string.my_nickname)
        binding.actionBar.tvCenter.setTextColor(resources.getColor(R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = View.VISIBLE

        setupRecyclerView()
        loadSavedNicknames()
        updateEmptyState()
    }

    override fun viewListener() {
        // Back button
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            if (fromSuccess) {
                // Navigate to Home if came from success screen
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                onBackPressed()
            }
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {
        // Action bar already initialized in initView
    }
    
    private fun setupRecyclerView() {
        savedNicknameAdapter = SavedNicknameAdapter(
            onDeleteClick = { nickname ->
                showDeleteConfirmDialog(nickname)
            },
            onEditClick = { nickname ->
                // Navigate to CustomizeNicknameActivity to edit the nickname
                startIntentRightToLeft(CustomizeNicknameActivity::class.java, "INPUT_NAME", nickname.nickname)
            }
        )

        binding.rvSavedNicknames.apply {
            layoutManager = LinearLayoutManager(this@MyNicknameActivity)
            adapter = savedNicknameAdapter
        }
    }
    
    private fun loadSavedNicknames() {
        val json = sharePreference.preferences.getString(PREF_KEY_SAVED_NICKNAMES, "[]")
        val type = object : TypeToken<List<SavedNicknameModel>>(){}.type
        val nicknames: List<SavedNicknameModel> = Gson().fromJson(json, type) ?: emptyList()
        
        savedNicknames.clear()
        savedNicknames.addAll(nicknames)
        savedNicknameAdapter.submitList(savedNicknames.toList())
    }
    
    private fun saveNicknamesToPrefs() {
        val json = Gson().toJson(savedNicknames)
        sharePreference.preferences.edit()
            .putString(PREF_KEY_SAVED_NICKNAMES, json)
            .apply()
    }
    
    private fun deleteNickname(nickname: SavedNicknameModel) {
        savedNicknames.remove(nickname)
        savedNicknameAdapter.submitList(savedNicknames.toList())
        saveNicknamesToPrefs()
        updateEmptyState()
        android.widget.Toast.makeText(this,
            getString(R.string.deleted, nickname.nickname), android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun updateEmptyState() {
        if (savedNicknames.isEmpty()) {
            binding.layoutEmpty.visibility = View.VISIBLE
            binding.rvSavedNicknames.visibility = View.GONE
        } else {
            binding.layoutEmpty.visibility = View.GONE
            binding.rvSavedNicknames.visibility = View.VISIBLE
        }
    }

    private fun showDeleteConfirmDialog(nickname: SavedNicknameModel) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = DialogConfirmBinding.inflate(LayoutInflater.from(this))
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Disable cancel on outside touch
        dialog.setCanceledOnTouchOutside(false)

        // Set dialog width to match parent with horizontal margin
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels
        dialog.window?.setLayout(width, android.view.ViewGroup.LayoutParams.MATCH_PARENT)

        // Customize dialog text and buttons for delete confirmation
        dialogBinding.tvDescription.text = getString(R.string.are_you_sure_want_to_delete_this_item)

        // Access included layout's binding
        val bottomBinding = dialogBinding.flBottom

        // Customize button text - No and Yes
        bottomBinding.tvBottomLeft.text = getString(R.string.no)
        bottomBinding.tvBottomLeft.setTextColor(resources.getColor(R.color.red_app, null))
        bottomBinding.tvBottomRight.text = getString(R.string.yes)

        // Customize button icons (optional - hide or change icons)
        bottomBinding.imvBottomLeft.visibility = View.GONE
        bottomBinding.imvBottomRight.visibility = View.GONE

        // Left button - No (Cancel)
        bottomBinding.btnBottomLeft.setOnSingleClick {
            dialog.dismiss()
        }

        // Right button - Yes (Delete/Confirm)
        bottomBinding.btnBottomRight.setOnSingleClick {
            deleteNickname(nickname)
            dialog.dismiss()
        }

        dialog.show()
    }
}

