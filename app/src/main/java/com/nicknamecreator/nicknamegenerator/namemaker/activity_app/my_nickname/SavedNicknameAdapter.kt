package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.my_nickname

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemSavedNicknameBinding

class SavedNicknameAdapter(
    private val onDeleteClick: (SavedNicknameModel) -> Unit,
    private val onEditClick: (SavedNicknameModel) -> Unit
) : BaseAdapter<SavedNicknameModel, ItemSavedNicknameBinding>(ItemSavedNicknameBinding::inflate) {

    override fun onBind(binding: ItemSavedNicknameBinding, item: SavedNicknameModel, position: Int) {
        binding.tvNickname.text = item.nickname
        binding.tvNickname.isSelected = true // Enable marquee effect

        // Copy button
        binding.btnCopy.setOnSingleClick {
            val clipboard = binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("nickname", item.nickname)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                binding.root.context,
                "${binding.root.context.getString(R.string.copied_message)}${item.nickname}",
                Toast.LENGTH_SHORT
            ).show()        }
        
        // Delete button
        binding.btnDelete.setOnSingleClick {
            onDeleteClick(item)
        }
        
        // Edit button
        binding.btnEdit.setOnSingleClick {
            onEditClick(item)
        }
    }
}

