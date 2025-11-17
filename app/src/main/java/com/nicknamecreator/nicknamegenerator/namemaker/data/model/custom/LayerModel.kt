package com.nicknamecreator.nicknamegenerator.namemaker.data.model.custom

import com.nicknamecreator.nicknamegenerator.namemaker.data.model.custom.ColorModel

data class LayerModel(
    val image: String,
    val isMoreColors: Boolean = false,
    var listColor: ArrayList<ColorModel> = arrayListOf()
)