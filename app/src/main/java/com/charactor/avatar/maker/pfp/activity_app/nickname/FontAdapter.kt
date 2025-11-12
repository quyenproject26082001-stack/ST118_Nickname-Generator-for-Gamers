package com.charactor.avatar.maker.pfp.activity_app.nickname

import androidx.core.content.res.ResourcesCompat
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemFontBinding

class FontAdapter(
    private val onFontClick: (String) -> Unit
) : BaseAdapter<FontModel, ItemFontBinding>(ItemFontBinding::inflate) {

    private var selectedPosition = -1

    override fun onBind(binding: ItemFontBinding, item: FontModel, position: Int) {
        binding.tvFont.text = "Aa"

        // Apply font to preview
        val fontResId = binding.root.context.resources.getIdentifier(
            item.fontFamily,
            "font",
            binding.root.context.packageName
        )

        val typeface = if (fontResId != 0) {
            ResourcesCompat.getFont(binding.root.context, fontResId)
        } else {
            ResourcesCompat.getFont(binding.root.context, R.font.roboto_regular)
        }
        binding.tvFont.typeface = typeface

        // Set selected state
        binding.tvFont.isSelected = (position == selectedPosition)

        binding.tvFont.setOnSingleClick {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify changes
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
            notifyItemChanged(position)

            onFontClick(item.fontFamily)
        }
    }
}

