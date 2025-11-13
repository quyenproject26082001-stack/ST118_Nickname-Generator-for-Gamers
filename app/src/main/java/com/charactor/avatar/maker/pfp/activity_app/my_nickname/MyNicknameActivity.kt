package com.charactor.avatar.maker.pfp.activity_app.my_nickname

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
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
                showEditNicknameDialog(nickname)
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

    private fun showEditNicknameDialog(nickname: SavedNicknameModel) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_edit_name)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set dialog width to match parent with horizontal margin 25dp
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels - (25 * 2 * displayMetrics.density).toInt()
        dialog.window?.setLayout(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT)

        val etEditName = dialog.findViewById<EditText>(R.id.etEditName)
        val btnUpdate = dialog.findViewById<AppCompatButton>(R.id.btnUpdate)

        // Set current nickname
        etEditName.setText(nickname.nickname)
        etEditName.setSelection(nickname.nickname.length)

        btnUpdate.setOnSingleClick {
            val newNickname = etEditName.text.toString().trim()
            if (newNickname.isNotEmpty()) {
                // Update nickname in list
                val index = savedNicknames.indexOfFirst { it.id == nickname.id }
                if (index != -1) {
                    savedNicknames[index] = SavedNicknameModel(id = nickname.id, nickname = newNickname)
                    savedNicknameAdapter.submitList(savedNicknames.toList())
                    saveNicknamesToPrefs()
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.nickname_updated), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.please_enter_a_nickname), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}

