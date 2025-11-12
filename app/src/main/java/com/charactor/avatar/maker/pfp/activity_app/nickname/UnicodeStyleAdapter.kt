package com.charactor.avatar.maker.pfp.activity_app.nickname

import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemFontBinding

class UnicodeStyleAdapter(
    private val onStyleClick: (StyleType) -> Unit
) : BaseAdapter<UnicodeStyleModel, ItemFontBinding>(ItemFontBinding::inflate) {

    private var selectedPosition = -1

    override fun onBind(binding: ItemFontBinding, item: UnicodeStyleModel, position: Int) {
        // Display preview text with Unicode style applied
        binding.tvFont.text = item.preview

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

            onStyleClick(item.styleType)
        }
    }
}
