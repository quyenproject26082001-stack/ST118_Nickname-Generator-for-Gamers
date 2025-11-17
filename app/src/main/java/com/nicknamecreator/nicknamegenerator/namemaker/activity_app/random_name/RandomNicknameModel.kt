package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name

import com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname.StyleType

data class RandomNicknameModel(
    val nickname: String, // Original nickname without style
    val category: String,
    val styledNickname: String, // Nickname with Unicode style applied
    val styleType: StyleType? = null, // Style type applied
    val leftSymbol: String? = null, // Left emoji symbol
    val rightSymbol: String? = null // Right emoji symbol
)

