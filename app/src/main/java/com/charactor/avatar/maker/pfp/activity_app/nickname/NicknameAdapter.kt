package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemNicknameBinding

class NicknameAdapter(
    private val onSaveClick: (NicknameModel) -> Unit
) : BaseAdapter<NicknameModel, ItemNicknameBinding>(ItemNicknameBinding::inflate) {

    override fun onBind(binding: ItemNicknameBinding, item: NicknameModel, position: Int) {
        binding.tvNickname.text = item.text
        binding.tvNickname.isSelected = true // Enable marquee effect

        // Set font based on fontFamily
        val fontResId = when (item.fontFamily) {
            "sigmar_regular" -> R.font.sigmar_regular
            "londrina_solid_regular" -> R.font.londrina_solid_regular
            "montserrat_bold" -> R.font.montserrat_bold
            "montserrat_italic" -> R.font.montserrat_italic
            "roboto_bold" -> R.font.roboto_bold
            "roboto_italic" -> R.font.roboto_italic
            "roboto_medium" -> R.font.roboto_medium
            "toruksc_regular" -> R.font.toruksc_regular
            else -> R.font.roboto_regular
        }
        
        val typeface = ResourcesCompat.getFont(binding.root.context, fontResId)
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
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}

