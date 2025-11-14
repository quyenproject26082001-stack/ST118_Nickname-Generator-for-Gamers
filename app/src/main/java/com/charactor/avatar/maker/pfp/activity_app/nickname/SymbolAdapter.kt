package com.charactor.avatar.maker.pfp.activity_app.nickname

import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemSymbolBinding

class SymbolAdapter(
    private val symbols: List<String>,
    private val onSymbolClick: (String) -> Unit
) : BaseAdapter<String, ItemSymbolBinding>(ItemSymbolBinding::inflate) {

    private var selectedPosition = -1

    fun setInitialSelection(symbol: String) {
        val position = symbols.indexOf(symbol)
        if (position != -1) {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify previous position to deselect
            if (previousPosition != -1 && previousPosition != position) {
                notifyItemChanged(previousPosition)
            }
            // Notify new position to select
            notifyItemChanged(position)
        }
    }

    override fun onBind(binding: ItemSymbolBinding, item: String, position: Int) {
        binding.tvSymbol.text = item

        // Set selected state
        val isSelected = (position == selectedPosition)
        binding.tvSymbol.isSelected = isSelected

        // Set elevation for shadow (only when selected)
        binding.cardSymbol.cardElevation = if (isSelected) {
            2f * binding.root.context.resources.displayMetrics.density
        } else {
            0f
        }

        binding.tvSymbol.setOnSingleClick {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify changes
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
            notifyItemChanged(position)

            onSymbolClick(item)
        }
    }
}

