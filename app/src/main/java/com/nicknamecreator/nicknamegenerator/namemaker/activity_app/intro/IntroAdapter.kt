package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.intro

import android.content.Context
import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.loadImageGlide
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.select
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setTextContent
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.strings
import com.nicknamecreator.nicknamegenerator.namemaker.data.model.IntroModel
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemIntroBinding

class IntroAdapter(val context: Context) : BaseAdapter<IntroModel, ItemIntroBinding>(
    ItemIntroBinding::inflate
) {
    override fun onBind(binding: ItemIntroBinding, item: IntroModel, position: Int) {
        binding.apply {
            loadImageGlide(root, item.image, imvImage, false)
            tvContent.text = context.strings(item.content)
            tvContent.select()
        }
    }
}