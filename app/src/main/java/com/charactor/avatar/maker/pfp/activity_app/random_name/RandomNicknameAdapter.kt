package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemRandomNicknameBinding

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

