package com.nicknamecreator.nicknamegenerator.namemaker.data.model

data class LanguageModel(
    val code: String,
    val name: String,
    val flag: Int,
    var activate: Boolean = false
)
