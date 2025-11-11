package com.charactor.avatar.maker.pfp.activity_app.my_nickname

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ActivityMyNicknameBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyNicknameActivity : BaseActivity<ActivityMyNicknameBinding>() {
    
    private lateinit var savedNicknameAdapter: SavedNicknameAdapter
    private val savedNicknames = mutableListOf<SavedNicknameModel>()
    
    companion object {
        private const val PREF_KEY_SAVED_NICKNAMES = "saved_nicknames"
    }
    
    override fun setViewBinding(): ActivityMyNicknameBinding {
        return ActivityMyNicknameBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Setup action bar
        binding.actionBar.btnActionBarLeft.setImageResource(R.drawable.ic_back)
        binding.actionBar.btnActionBarLeft.visibility = View.VISIBLE

        binding.actionBar.tvCenter.text = "My nickname"
        binding.actionBar.tvCenter.setTextColor(resources.getColor(R.color.red_app, null))
        binding.actionBar.tvCenter.visibility = View.VISIBLE
        
        setupRecyclerView()
        loadSavedNicknames()
        updateEmptyState()
    }

    override fun viewListener() {
        // Back button
        binding.actionBar.btnActionBarLeft.setOnSingleClick {
            onBackPressed()
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
                deleteNickname(nickname)
            },
            onEditClick = { nickname ->
                // TODO: Navigate to edit screen
                android.widget.Toast.makeText(this, "Edit: ${nickname.nickname}", android.widget.Toast.LENGTH_SHORT).show()
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
        android.widget.Toast.makeText(this, "Deleted: ${nickname.nickname}", android.widget.Toast.LENGTH_SHORT).show()
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
}

