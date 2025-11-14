package com.charactor.avatar.maker.pfp.activity_app.my_nickname

import com.charactor.avatar.maker.pfp.activity_app.nickname.StyleType

data class SavedNicknameModel(
    val id: Long = System.currentTimeMillis(),
    val nickname: String,
    val originalText: String? = null,  // Text gốc không style
    val leftSymbol: String? = null,     // Symbol bên trái
    val rightSymbol: String? = null,    // Symbol bên phải
    val styleType: StyleType? = null    // Style đã apply
)

