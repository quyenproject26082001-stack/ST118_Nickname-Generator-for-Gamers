package com.charactor.avatar.maker.pfp.activity_app.nickname

import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemSymbolBinding

class SymbolAdapter(
    private val onSymbolClick: (String) -> Unit
) : BaseAdapter<String, ItemSymbolBinding>(ItemSymbolBinding::inflate) {

    override fun onBind(binding: ItemSymbolBinding, item: String, position: Int) {
        binding.tvSymbol.text = item
        
        binding.tvSymbol.setOnSingleClick {
            onSymbolClick(item)
        }
    }
}

