package com.charactor.avatar.maker.pfp.activity_app.nickname

import androidx.core.content.res.ResourcesCompat
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemFontBinding

class FontAdapter(
    private val onFontClick: (String) -> Unit
) : BaseAdapter<FontModel, ItemFontBinding>(ItemFontBinding::inflate) {

    override fun onBind(binding: ItemFontBinding, item: FontModel, position: Int) {
        binding.tvFont.text = item.displayName
        
        // Apply font to preview
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
        binding.tvFont.typeface = typeface
        
        binding.tvFont.setOnSingleClick {
            onFontClick(item.fontFamily)
        }
    }
}

