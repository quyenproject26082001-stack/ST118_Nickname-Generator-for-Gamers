package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname

import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.data.model.StyleType
import com.nicknamecreator.nicknamegenerator.namemaker.data.model.UnicodeStyleModel
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemFontBinding

class UnicodeStyleAdapter(
    private val styles: List<UnicodeStyleModel>,
    private val onStyleClick: (StyleType) -> Unit
) : BaseAdapter<UnicodeStyleModel, ItemFontBinding>(ItemFontBinding::inflate) {

    private var selectedPosition = -1

    // Callback to notify fragment to scroll to position
    var onScrollToPosition: ((Int) -> Unit)? = null

    fun setInitialSelection(styleType: StyleType) {
        val position = styles.indexOfFirst { it.styleType == styleType }
        if (position != -1) {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify previous position to deselect
            if (previousPosition != -1 && previousPosition != position) {
                notifyItemChanged(previousPosition)
            }
            // Notify new position to select
            notifyItemChanged(position)

            // Scroll to selected position
            onScrollToPosition?.invoke(position)
        }
    }

    override fun onBind(binding: ItemFontBinding, item: UnicodeStyleModel, position: Int) {
        // Display preview text with Unicode style applied
        binding.tvFont.text = item.preview

        // Add extra vertical padding for styles with diacritics above/below
        val extraPaddingDp = if (item.styleType in listOf(
                StyleType.CHAOTIC_MIX,
                StyleType.ZALGO_ARROWS,
                StyleType.ZALGO_MEDIUM,
                StyleType.ZALGO_HEAVY
            )) 18 else 7

        val paddingPx = (extraPaddingDp * binding.root.context.resources.displayMetrics.density).toInt()
        binding.tvFont.setPadding(
            binding.tvFont.paddingLeft,
            paddingPx,
            binding.tvFont.paddingRight,
            paddingPx
        )

        // Set selected state
        val isSelected = (position == selectedPosition)
        binding.tvFont.isSelected = isSelected

        // Set elevation for shadow (only when selected)
        binding.cardFont.cardElevation = if (isSelected) {
            3.7f * binding.root.context.resources.displayMetrics.density
        } else {
            0f
        }

        // Enable marquee for selected item
        if (isSelected) {
            binding.tvFont.isSelected = true
        }

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
