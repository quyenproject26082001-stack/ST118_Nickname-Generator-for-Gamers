package com.charactor.avatar.maker.pfp.activity_app.random_name

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemRandomNicknameBinding

class RandomNicknameAdapter(
    private val onSaveClick: (RandomNicknameModel) -> Unit
) : BaseAdapter<RandomNicknameModel, ItemRandomNicknameBinding>(ItemRandomNicknameBinding::inflate) {

    override fun onBind(binding: ItemRandomNicknameBinding, item: RandomNicknameModel, position: Int) {
        binding.tvNickname.text = item.nickname
        
        // Copy button
        binding.btnCopy.setOnSingleClick {
            val clipboard = binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("nickname", item.nickname)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(binding.root.context, "Copied: ${item.nickname}", Toast.LENGTH_SHORT).show()
        }
        
        // Save button
        binding.btnSave.setOnSingleClick {
            onSaveClick(item)
        }
    }
}

