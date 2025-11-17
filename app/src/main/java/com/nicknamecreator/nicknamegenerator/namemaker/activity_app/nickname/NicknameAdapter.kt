package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.nicknamecreator.nicknamegenerator.namemaker.R
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemNicknameBinding

class NicknameAdapter(
    private val onSaveClick: (NicknameModel) -> Unit
) : BaseAdapter<NicknameModel, ItemNicknameBinding>(ItemNicknameBinding::inflate) {

    override fun onBind(binding: ItemNicknameBinding, item: NicknameModel, position: Int) {
        binding.tvNickname.text = item.text
        binding.tvNickname.isSelected = true // Enable marquee effect

        // Use Roboto Regular font for all nicknames (Unicode styles are in the text itself)
        val typeface = ResourcesCompat.getFont(binding.root.context, R.font.roboto_regular)
        binding.tvNickname.typeface = typeface

        // Copy button
        binding.btnCopy.setOnSingleClick {
            copyToClipboard(binding.root.context, item.text)
        }

        // Save button
        binding.btnSave.setOnSingleClick {
            onSaveClick(item)
        }
    }

    private fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("nickname", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, context.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }
}

