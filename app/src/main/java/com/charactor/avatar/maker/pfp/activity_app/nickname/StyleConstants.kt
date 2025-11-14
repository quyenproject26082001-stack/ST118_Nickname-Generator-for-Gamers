package com.charactor.avatar.maker.pfp.activity_app.nickname

/**
 * Central repository for all Unicode styles used across the app
 * This ensures consistency between NicknameActivity, CategoryDetailActivity, and CustomizeNicknameActivity
 */
object StyleConstants {
    
    /**
     * Basic Unicode styles - commonly used and compatible with most platforms
     * Used in NicknameActivity for quick nickname generation (16 styles)
     */
    val BASIC_STYLES = listOf(
        StyleType.BOLD,
        StyleType.ITALIC,
        StyleType.BOLD_ITALIC,
        StyleType.SCRIPT,
        StyleType.BOLD_SCRIPT,
        StyleType.FRAKTUR,
        StyleType.DOUBLE_STRUCK,
        StyleType.SANS_BOLD,
        StyleType.MONOSPACE,
        StyleType.CIRCLED,
        StyleType.SQUARED,
        StyleType.NEGATIVE_CIRCLED,
        StyleType.FULLWIDTH,
        StyleType.SMALL_CAPS,
        StyleType.UNDERLINE,
        StyleType.STRIKETHROUGH
    )
    
    /**
     * Popular Unicode styles - most commonly used styles with good compatibility
     * Used in CategoryDetailActivity and NicknameDataSource for random nickname generation (24 styles)
     */
    val POPULAR_STYLES = listOf(
        // Mathematical Alphanumeric (most popular)
        StyleType.BOLD,
        StyleType.ITALIC,
        StyleType.BOLD_ITALIC,
        StyleType.SCRIPT,
        StyleType.BOLD_SCRIPT,
        StyleType.FRAKTUR,
        StyleType.DOUBLE_STRUCK,
        StyleType.SANS_BOLD,
        StyleType.MONOSPACE,
        
        // Enclosed Characters (popular)
        StyleType.CIRCLED,
        StyleType.SQUARED,
        
        // Special Unicode Blocks (popular)
        StyleType.FULLWIDTH,
        StyleType.SMALL_CAPS,
        
        // Combining Characters (popular)
        StyleType.UNDERLINE,
        StyleType.STRIKETHROUGH,
        StyleType.BOLD_UNDERLINE,
        StyleType.ITALIC_UNDERLINE,
        
        // Decorative Styles (popular)
        StyleType.STARS,
        StyleType.HEARTS,
        StyleType.SPARKLES,
        StyleType.AESTHETIC,
        StyleType.VAPORWAVE,
        StyleType.BUBBLE_TEXT,
        StyleType.FANCY_MIX
    )
    
    /**
     * All available Unicode styles - complete collection
     * Used in CustomizeNicknameActivity's UnicodeStyleFragment (100+ styles)
     */
    val ALL_STYLES = listOf(
        // Mathematical Alphanumeric (13)
        StyleType.BOLD,
        StyleType.ITALIC,
        StyleType.BOLD_ITALIC,
        StyleType.SCRIPT,
        StyleType.BOLD_SCRIPT,
        StyleType.FRAKTUR,
        StyleType.BOLD_FRAKTUR,
        StyleType.DOUBLE_STRUCK,
        StyleType.SANS,
        StyleType.SANS_BOLD,
        StyleType.SANS_ITALIC,
        StyleType.SANS_BOLD_ITALIC,
        StyleType.MONOSPACE,

        // Enclosed Characters (5)
        StyleType.CIRCLED,
        StyleType.SQUARED,
        StyleType.NEGATIVE_CIRCLED,
        StyleType.NEGATIVE_SQUARED,
        StyleType.PARENTHESIZED,

        // Special Unicode Blocks (5)
        StyleType.FULLWIDTH,
        StyleType.SMALL_CAPS,
        StyleType.SUPERSCRIPT,
        StyleType.SUBSCRIPT,
        StyleType.INVERTED,

        // Basic Combining Characters (8)
        StyleType.STRIKETHROUGH,
        StyleType.UNDERLINE,
        StyleType.OVERLINE,
        StyleType.SLASH,
        StyleType.DOTTED,
        StyleType.DOUBLE_UNDERLINE,
        StyleType.TILDE,
        StyleType.RING_ABOVE,

        // More Combining Diacritics (8)
        StyleType.DIAERESIS,
        StyleType.ACUTE,
        StyleType.GRAVE,
        StyleType.CIRCUMFLEX,
        StyleType.CARON,
        StyleType.BREVE,
        StyleType.MACRON,
        StyleType.DOUBLE_ACUTE,

        // Mathematical Style + Underline (5)
        StyleType.SCRIPT_UNDERLINE,
        StyleType.FRAKTUR_UNDERLINE,
        StyleType.MONOSPACE_UNDERLINE,
        StyleType.SANS_BOLD_UNDERLINE,
        StyleType.DOUBLE_STRUCK_UNDERLINE,

        // Bold/Italic Combinations (6)
        StyleType.BOLD_UNDERLINE,
        StyleType.ITALIC_UNDERLINE,
        StyleType.BOLD_STRIKETHROUGH,
        StyleType.BOLD_DOTTED,
        StyleType.ITALIC_DOTTED,
        StyleType.BOLD_TILDE,

        // Italic/Other Combinations (1)
        StyleType.ITALIC_STRIKETHROUGH,

        // More Mathematical + Combining (7)
        StyleType.BOLD_CIRCUMFLEX,
        StyleType.ITALIC_CARON,
        StyleType.SCRIPT_TILDE,
        StyleType.MONOSPACE_STRIKETHROUGH,
        StyleType.SANS_BOLD_STRIKETHROUGH,
        StyleType.FRAKTUR_DOTTED,
        StyleType.DOUBLE_STRUCK_STRIKETHROUGH,

        // Zalgo/Glitch (3)
        StyleType.ZALGO_LIGHT,
        StyleType.ZALGO_MEDIUM,
        StyleType.ZALGO_HEAVY,

        // Decorative Brackets (15)
        StyleType.SQUARE_BRACKETS,
        StyleType.DOUBLE_BRACKETS,
        StyleType.CURLY_BRACKETS,
        StyleType.WHITE_BRACKETS,
        StyleType.TORTOISE_BRACKETS,
        StyleType.ANGLE_BRACKETS,
        StyleType.DOUBLE_ANGLE_BRACKETS,
        StyleType.CORNER_BRACKETS,
        StyleType.FLOOR_BRACKETS,
        StyleType.PARENTHESES,
        StyleType.SQUARE_PARENTHESES,
        StyleType.CURLY_PARENTHESES,
        StyleType.ARROW_BRACKETS,
        StyleType.QUOTATION_MARKS,
        StyleType.SINGLE_QUOTES,

        // Emoji Decorations (5)
        StyleType.STARS,
        StyleType.HEARTS,
        StyleType.SPARKLES,
        StyleType.CROWN,
        StyleType.FLOWERS,

        // Arrow Decorations (5)
        StyleType.ARROWS_LEFT,
        StyleType.ARROWS_RIGHT,
        StyleType.ARROWS_BOTH,
        StyleType.DOUBLE_ARROWS,
        StyleType.TRIANGLE_ARROWS,

        // Box Drawing (4)
        StyleType.BOX_SINGLE,
        StyleType.BOX_DOUBLE,
        StyleType.BOX_ROUNDED,
        StyleType.BOX_HEAVY,

        // Block Backgrounds (4)
        StyleType.BLOCK_LIGHT,
        StyleType.BLOCK_MEDIUM,
        StyleType.BLOCK_HEAVY,
        StyleType.BLOCK_FULL,

        // Character Substitution / Lookalike (7)
        StyleType.CYRILLIC_LOOKALIKE,
        StyleType.GREEK_LOOKALIKE,
        StyleType.ASIAN_MIX,
        StyleType.MATH_SYMBOLS,
        StyleType.WEIRD_MIX,
        StyleType.SQUARED_NEGATIVE_LOOKALIKE,
        StyleType.CURRENCY_MIX,

        // Mixed / Random Styles (13)
        StyleType.MIXED_LIGHT,
        StyleType.MIXED_MEDIUM,
        StyleType.MIXED_HEAVY,
        StyleType.ALTERNATING_CASE,
        StyleType.RANDOM_CASE,
        StyleType.MIXED_SUBSTITUTION,
        StyleType.ALTERNATING_STYLES,
        StyleType.WAVE_STYLE,
        StyleType.BUBBLE_TEXT,
        StyleType.FANCY_MIX,
        StyleType.AESTHETIC,
        StyleType.VAPORWAVE,
        StyleType.GLITCH_MIX,
        StyleType.CRAZY_MIX,

        // Mixed Script Styles (15)
        StyleType.ARMENIAN,
        StyleType.THAI_LAO_MIX,
        StyleType.JAPANESE_MIX,
        StyleType.MEDIEVAL_LATIN,
        StyleType.BOPOMOFO_CJK,
        StyleType.YI_SYLLABLES,
        StyleType.CANADIAN_ABORIGINAL,
        StyleType.CHEROKEE,
        StyleType.LISU,
        StyleType.HEBREW_GREEK_MIX,
        StyleType.GUJARATI_MIX,
        StyleType.THAI_ARMENIAN_MIX,
        StyleType.CYRILLIC_ARMENIAN_MIX,
        StyleType.GREEK_CYRILLIC_MIX,
        StyleType.LATIN_GREEK_VIETNAMESE_MIX,

        // IPA & Phonetic Styles (3)
        StyleType.IPA_PHONETIC,
        StyleType.IPA_EXTENDED,
        StyleType.PHONETIC_EXTENSIONS,

        // Advanced Greek Variants (3)
        StyleType.GREEK_EXTENDED,
        StyleType.GREEK_COPTIC_MIX,
        StyleType.GREEK_ARCHAIC,

        // Symbol Separators (6)
        StyleType.SEPARATOR_N_ARY,
        StyleType.SEPARATOR_APL,
        StyleType.SEPARATOR_STAR,
        StyleType.SEPARATOR_DOT,
        StyleType.SEPARATOR_DIAMOND,
        StyleType.SEPARATOR_CIRCLE,

        // Complex Combining Marks (8)
        StyleType.COMBINING_INVERTED_BRIDGE,
        StyleType.COMBINING_CANDRABINDU,
        StyleType.COMBINING_ZIGZAG,
        StyleType.COMBINING_ARROW,
        StyleType.DOUBLE_STRUCK_MULTI_DIACRITICS,
        StyleType.DIACRITICS_RANDOM_HEAVY,
        StyleType.SUPERSCRIPT_DIACRITICS_MIX,
        StyleType.SUBSCRIPT_DIACRITICS_MIX,

        // Mirrored & Flipped Styles (2)
        StyleType.MIRRORED_REVERSED,
        StyleType.UPSIDE_DOWN_MIRRORED,

        // Heavy Effects (3)
        StyleType.ZALGO_ARROWS,
        StyleType.GLITCH_HEAVY_MARKS,
        StyleType.CHAOTIC_MIX
    )
    
    /**
     * Get a random selection of basic styles
     * Used in NicknameActivity for quick generation
     */
    fun getRandomBasicStyles(count: Int = 12): List<StyleType> {
        return BASIC_STYLES.shuffled().take(count)
    }
    
    /**
     * Get a random selection of popular styles
     * Used in CategoryDetailActivity for random nickname generation
     */
    fun getRandomPopularStyles(count: Int = 20): List<StyleType> {
        return POPULAR_STYLES.shuffled().take(count)
    }
    
    /**
     * Get all styles for customization
     * Used in CustomizeNicknameActivity
     */
    fun getAllStyles(): List<StyleType> {
        return ALL_STYLES
    }
}

