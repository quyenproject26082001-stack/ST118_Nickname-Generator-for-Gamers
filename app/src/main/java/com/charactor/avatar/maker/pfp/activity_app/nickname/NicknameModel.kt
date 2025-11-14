package com.charactor.avatar.maker.pfp.activity_app.nickname

data class NicknameModel(
    val text: String, // Text with Unicode style already applied
    val originalText: String? = null, // Original text without style
    val leftSymbol: String? = null, // Left symbol
    val rightSymbol: String? = null, // Right symbol
    val styleType: StyleType? = null // Style type applied
)

