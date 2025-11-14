package com.charactor.avatar.maker.pfp.activity_app.nickname

/**
 * Central repository for all symbols/emoji used across the app
 * This ensures consistency between NicknameActivity, CustomizeNicknameActivity, and other screens
 */
object SymbolConstants {
    
    /**
     * Simple symbols used in NicknameActivity for quick nickname generation
     * These are basic Unicode symbols (not emoji)
     */
    val SIMPLE_SYMBOLS = listOf(
        "★" to "★",
        "♥" to "♥",
        "◆" to "◆",
        "●" to "●",
        "▲" to "▲",
        "✦" to "✦",
        "❖" to "❖",
        "◈" to "◈",
        "✧" to "✧",
        "♦" to "♦",
        "◇" to "◇",
        "○" to "○",
        "△" to "△",
        "▽" to "▽",
        "◉" to "◉",
        "◎" to "◎"
    )
    
    /**
     * Full emoji collection used in CustomizeNicknameActivity's SymbolFragment
     * Organized by categories for better user experience
     */
    val ALL_EMOJIS = listOf(
        // Stars & Sparkles
        "⭐", "🌟", "✨", "💫", "⚡", "☄️", "🌠", "✴️", "🔆", "🌞",

        // Crowns & Royalty
        "👑", "💎", "💍", "👸", "🤴", "🏆", "🥇", "🥈", "🥉", "🏅",

        // Fire & Energy
        "🔥", "💥", "⚡", "🌪️", "🌊", "❄️", "☃️", "🌈", "🌤️", "⛈️",

        // Animals & Nature
        "🦅", "🦁", "🐺", "🐉", "🦄", "🐯", "🦊", "🐻", "🐼", "🦋",
        "🐝", "🦂", "🦇", "🕷️", "🐍", "🦎", "🐢", "🦖", "🦕", "🐙",

        // Gaming & Sports
        "🎮", "🎯", "🎲", "🎰", "🎪", "🎭", "🎬", "🎤", "🎧", "🎸",
        "🎹", "🎺", "🎻", "🥁", "🎨", "🖌️", "🖍️", "✏️", "⚽", "🏀",
        "🏈", "⚾", "🎾", "🏐", "🏉", "🎱", "🏓", "🏸", "🥊", "⛳",

        // Weapons & Combat
        "⚔️", "🗡️", "🔪", "🏹", "🛡️", "🔱", "⚒️", "🔧", "🔨", "⛏️",
        "🪓", "💣", "🧨", "🔫", "🏴‍☠️", "☠️", "💀", "👻", "👽", "🤖",

        // Hearts & Love
        "❤️", "💕", "💖", "💗", "💘", "💙", "💚", "💛", "🧡", "💜",
        "🖤", "🤍", "🤎", "💝", "💞", "💟", "❣️", "💔", "❤️‍🔥", "❤️‍🩹",

        // Food & Drinks
        "🍕", "🍔", "🍟", "🌭", "🍿", "🧂", "🥓", "🥚", "🍳", "🧇",
        "🥞", "🧈", "🍞", "🥐", "🥨", "🥯", "🥖", "🧀", "🥗", "🥙",
        "🌮", "🌯", "🥪", "🍖", "🍗", "🥩", "🍠", "🍱", "🍘", "🍙",

        // Faces & Emotions
        "😀", "😃", "😄", "😁", "😆", "😅", "🤣", "😂", "🙂", "🙃",
        "😉", "😊", "😇", "🥰", "😍", "🤩", "😘", "😗", "😚", "😙",
        "😋", "😛", "😜", "🤪", "😝", "🤑", "🤗", "🤭", "🤫", "🤔",

        // Weather & Sky
        "☀️", "🌤️", "⛅", "🌥️", "☁️", "🌦️", "🌧️", "⛈️", "🌩️", "🌨️",
        "❄️", "☃️", "⛄", "🌬️", "💨", "🌪️", "🌫️", "🌈", "☂️", "⛱️",

        // Symbols & Shapes
        "💯", "🔱", "⚜️", "〽️", "⚠️", "🚸", "⛔", "🚫", "🚳", "🚭",
        "🚯", "🚱", "🚷", "📵", "🔞", "☢️", "☣️", "⬆️", "↗️", "➡️",

        // Hands & Gestures
        "👍", "👎", "👊", "✊", "🤛", "🤜", "🤞", "✌️", "🤟", "🤘",
        "👌", "🤌", "🤏", "👈", "👉", "👆", "👇", "☝️", "✋", "🤚",

        // Flowers & Plants
        "💐", "🌸", "💮", "🏵️", "🌹", "🥀", "🌺", "🌻", "🌼", "🌷",
        "🌱", "🌲", "🌳", "🌴", "🌵", "🌾", "🌿", "☘️", "🍀", "🍁",

        // Moon & Stars
        "🌙", "🌚", "🌛", "🌜", "🌝", "🌞", "⭐", "🌟", "✨", "⚡",
        "☄️", "💫", "🌠", "🌌", "🌃", "🌆", "🌇", "🌉", "🌁", "🌄",

        // Objects & Tools
        "📱", "💻", "⌨️", "🖥️", "🖨️", "🖱️", "🖲️", "🕹️", "🗜️", "💾",
        "💿", "📀", "📼", "📷", "📸", "📹", "🎥", "📽️", "🎞️", "📞",
        "☎️", "📟", "📠", "📺", "📻", "🎙️", "🎚️", "🎛️", "🧭", "⏱️",
        "⏲️", "⏰", "🕰️", "⌛", "⏳", "📡", "🔋", "🔌", "💡", "🔦",
        "🕯️", "🪔", "🧯", "🛢️", "💸", "💵", "💴", "💶", "💷", "💰"
    )
    
    /**
     * Get a random selection of simple symbols
     * Used in NicknameActivity for quick generation
     */
    fun getRandomSimpleSymbols(count: Int = 8): List<Pair<String, String>> {
        return SIMPLE_SYMBOLS.shuffled().take(count)
    }
    
    /**
     * Get a random selection of emojis
     * Used for variety in nickname generation
     */
    fun getRandomEmojis(count: Int = 20): List<String> {
        return ALL_EMOJIS.shuffled().take(count)
    }
}

