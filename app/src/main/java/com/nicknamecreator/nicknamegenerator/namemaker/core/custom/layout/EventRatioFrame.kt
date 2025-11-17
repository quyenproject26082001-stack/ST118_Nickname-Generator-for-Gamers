package com.nicknamecreator.nicknamegenerator.namemaker.core.custom.layout

import android.widget.ImageView
import com.nicknamecreator.nicknamegenerator.namemaker.core.custom.imageview.StrokeImageView

interface EventRatioFrame {
    fun onImageClick(image: StrokeImageView, btnEdit: ImageView)
}