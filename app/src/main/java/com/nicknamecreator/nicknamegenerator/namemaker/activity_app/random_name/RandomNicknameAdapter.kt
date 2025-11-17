package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemRandomNicknameBinding

class RandomNicknameAdapter(
    private val onSaveClick: (RandomNicknameModel) -> Unit
) : BaseAdapter<RandomNicknameModel, ItemRandomNicknameBinding>(ItemRandomNicknameBinding::inflate) {

    override fun onBind(binding: ItemRandomNicknameBinding, item: RandomNicknameModel, position: Int) {
        // Display styled nickname
        binding.tvNickname.text = item.styledNickname

        // Copy button - copy the styled nickname
        binding.btnCopy.setOnSingleClick {
            val clipboard = binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("nickname", item.styledNickname)
            clipboard.setPrimaryClip(clip)
            val message = binding.root.context.getString(R.string.copied_message) + item.styledNickname
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }

        // Save button
        binding.btnSave.setOnSingleClick {
            onSaveClick(item)
        }
    }
}

