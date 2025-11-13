package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.charactor.avatar.maker.pfp.core.utils.UnicodeStyleConverter
import com.charactor.avatar.maker.pfp.databinding.FragmentStyleTextBinding

class UnicodeStyleFragment : Fragment() {

    private var _binding: FragmentStyleTextBinding? = null
    private val binding get() = _binding!!

    private var onStyleSelected: ((StyleType) -> Unit)? = null
    private var currentPreviewText: String = "Aa"
    private var adapter: UnicodeStyleAdapter? = null

    companion object {
        private const val ARG_PREVIEW_TEXT = "preview_text"

        fun newInstance(previewText: String, onStyleSelected: (StyleType) -> Unit): UnicodeStyleFragment {
            return UnicodeStyleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PREVIEW_TEXT, previewText)
                }
                this.onStyleSelected = onStyleSelected
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStyleTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get preview text from arguments
        currentPreviewText = arguments?.getString(ARG_PREVIEW_TEXT) ?: "Aa"

        setupRecyclerView()
    }

    // Public method to update preview text from Activity
    fun updatePreviewText(newText: String) {
        currentPreviewText = newText
        refreshStylesList()
    }

    private fun setupRecyclerView() {
        val conv = UnicodeStyleConverter
        val previewText = currentPreviewText

        val styles = listOf(
            // Mathematical Alphanumeric (13)
            UnicodeStyleModel("Bold", StyleType.BOLD, conv.toBold(previewText)),
            UnicodeStyleModel("Italic", StyleType.ITALIC, conv.toItalic(previewText)),
            UnicodeStyleModel("Bold Italic", StyleType.BOLD_ITALIC, conv.toBoldItalic(previewText)),
            UnicodeStyleModel("Script", StyleType.SCRIPT, conv.toScript(previewText)),
            UnicodeStyleModel("Bold Script", StyleType.BOLD_SCRIPT, conv.toBoldScript(previewText)),
            UnicodeStyleModel("Fraktur", StyleType.FRAKTUR, conv.toFraktur(previewText)),
            UnicodeStyleModel("Bold Fraktur", StyleType.BOLD_FRAKTUR, conv.toBoldFraktur(previewText)),
            UnicodeStyleModel("Double Struck", StyleType.DOUBLE_STRUCK, conv.toDoubleStruck(previewText)),
            UnicodeStyleModel("Sans", StyleType.SANS, conv.toSans(previewText)),
            UnicodeStyleModel("Sans Bold", StyleType.SANS_BOLD, conv.toSansBold(previewText)),
            UnicodeStyleModel("Sans Italic", StyleType.SANS_ITALIC, conv.toSansItalic(previewText)),
            UnicodeStyleModel("Sans Bold Italic", StyleType.SANS_BOLD_ITALIC, conv.toSansBoldItalic(previewText)),
            UnicodeStyleModel("Monospace", StyleType.MONOSPACE, conv.toMonospace(previewText)),

            // Enclosed Characters (5)
            UnicodeStyleModel("Circled", StyleType.CIRCLED, conv.toCircled(previewText)),
            UnicodeStyleModel("Squared", StyleType.SQUARED, conv.toSquared(previewText)),
            UnicodeStyleModel("Negative Circled", StyleType.NEGATIVE_CIRCLED, conv.toNegativeCircled(previewText)),
            UnicodeStyleModel("Negative Squared", StyleType.NEGATIVE_SQUARED, conv.toNegativeSquared(previewText)),
            UnicodeStyleModel("Parenthesized", StyleType.PARENTHESIZED, conv.toParenthesized(previewText)),

            // Special Unicode Blocks (5)
            UnicodeStyleModel("Fullwidth", StyleType.FULLWIDTH, conv.toFullwidth(previewText)),
            UnicodeStyleModel("Small Caps", StyleType.SMALL_CAPS, conv.toSmallCaps(previewText)),
            UnicodeStyleModel("Superscript", StyleType.SUPERSCRIPT, conv.toSuperscript(previewText)),
            UnicodeStyleModel("Subscript", StyleType.SUBSCRIPT, conv.toSubscript(previewText)),
            UnicodeStyleModel("Inverted", StyleType.INVERTED, conv.toInverted(previewText)),

            // Basic Combining (8)
            UnicodeStyleModel("Strikethrough", StyleType.STRIKETHROUGH, conv.toStrikethrough(previewText)),
            UnicodeStyleModel("Underline", StyleType.UNDERLINE, conv.toUnderline(previewText)),
            UnicodeStyleModel("Overline", StyleType.OVERLINE, conv.toOverline(previewText)),
            UnicodeStyleModel("Slash", StyleType.SLASH, conv.toSlash(previewText)),
            UnicodeStyleModel("Dotted", StyleType.DOTTED, conv.toDotted(previewText)),
            UnicodeStyleModel("Double Underline", StyleType.DOUBLE_UNDERLINE, conv.toDoubleUnderline(previewText)),
            UnicodeStyleModel("Tilde", StyleType.TILDE, conv.toTilde(previewText)),
            UnicodeStyleModel("Ring Above", StyleType.RING_ABOVE, conv.toRingAbove(previewText)),

            // More Diacritics (8)
            UnicodeStyleModel("Diaeresis", StyleType.DIAERESIS, conv.toDiaeresis(previewText)),
            UnicodeStyleModel("Acute", StyleType.ACUTE, conv.toAcute(previewText)),
            UnicodeStyleModel("Grave", StyleType.GRAVE, conv.toGrave(previewText)),
            UnicodeStyleModel("Circumflex", StyleType.CIRCUMFLEX, conv.toCircumflex(previewText)),
            UnicodeStyleModel("Caron", StyleType.CARON, conv.toCaron(previewText)),
            UnicodeStyleModel("Breve", StyleType.BREVE, conv.toBreve(previewText)),
            UnicodeStyleModel("Macron", StyleType.MACRON, conv.toMacron(previewText)),
            UnicodeStyleModel("Double Acute", StyleType.DOUBLE_ACUTE, conv.toDoubleAcute(previewText)),

            // Math + Underline (5)
            UnicodeStyleModel("Script Underline", StyleType.SCRIPT_UNDERLINE, conv.toScriptUnderline(previewText)),
            UnicodeStyleModel("Fraktur Underline", StyleType.FRAKTUR_UNDERLINE, conv.toFrakturUnderline(previewText)),
            UnicodeStyleModel("Monospace Underline", StyleType.MONOSPACE_UNDERLINE, conv.toMonospaceUnderline(previewText)),
            UnicodeStyleModel("Sans Bold Underline", StyleType.SANS_BOLD_UNDERLINE, conv.toSansBoldUnderline(previewText)),
            UnicodeStyleModel("Double Struck Underline", StyleType.DOUBLE_STRUCK_UNDERLINE, conv.toDoubleStruckUnderline(previewText)),

            // Bold/Italic Combos (6)
            UnicodeStyleModel("Bold Underline", StyleType.BOLD_UNDERLINE, conv.toBoldUnderline(previewText)),
            UnicodeStyleModel("Italic Underline", StyleType.ITALIC_UNDERLINE, conv.toItalicUnderline(previewText)),
            UnicodeStyleModel("Bold Strikethrough", StyleType.BOLD_STRIKETHROUGH, conv.toBoldStrikethrough(previewText)),
            UnicodeStyleModel("Bold Dotted", StyleType.BOLD_DOTTED, conv.toBoldDotted(previewText)),
            UnicodeStyleModel("Italic Dotted", StyleType.ITALIC_DOTTED, conv.toItalicDotted(previewText)),
            UnicodeStyleModel("Bold Tilde", StyleType.BOLD_TILDE, conv.toBoldTilde(previewText)),

            // Italic/Other (1)
            UnicodeStyleModel("Italic Strikethrough", StyleType.ITALIC_STRIKETHROUGH, conv.toItalicStrikethrough(previewText)),

            // More Math + Combining (7)
            UnicodeStyleModel("Bold Circumflex", StyleType.BOLD_CIRCUMFLEX, conv.toBoldCircumflex(previewText)),
            UnicodeStyleModel("Italic Caron", StyleType.ITALIC_CARON, conv.toItalicCaron(previewText)),
            UnicodeStyleModel("Script Tilde", StyleType.SCRIPT_TILDE, conv.toScriptTilde(previewText)),
            UnicodeStyleModel("Monospace Strikethrough", StyleType.MONOSPACE_STRIKETHROUGH, conv.toMonospaceStrikethrough(previewText)),
            UnicodeStyleModel("Sans Bold Strikethrough", StyleType.SANS_BOLD_STRIKETHROUGH, conv.toSansBoldStrikethrough(previewText)),
            UnicodeStyleModel("Fraktur Dotted", StyleType.FRAKTUR_DOTTED, conv.toFrakturDotted(previewText)),
            UnicodeStyleModel("Double Struck Strikethrough", StyleType.DOUBLE_STRUCK_STRIKETHROUGH, conv.toDoubleStruckStrikethrough(previewText)),

            // Zalgo/Glitch (3)
            UnicodeStyleModel("Zalgo Light", StyleType.ZALGO_LIGHT, conv.toZalgoLight(previewText)),
            UnicodeStyleModel("Zalgo Medium", StyleType.ZALGO_MEDIUM, conv.toZalgoMedium(previewText)),
            UnicodeStyleModel("Zalgo Heavy", StyleType.ZALGO_HEAVY, conv.toZalgoHeavy(previewText)),

            // Decorative Brackets (15)
            UnicodeStyleModel("Square Brackets", StyleType.SQUARE_BRACKETS, conv.toSquareBrackets(previewText)),
            UnicodeStyleModel("Double Brackets", StyleType.DOUBLE_BRACKETS, conv.toDoubleBrackets(previewText)),
            UnicodeStyleModel("Curly Brackets", StyleType.CURLY_BRACKETS, conv.toCurlyBrackets(previewText)),
            UnicodeStyleModel("White Brackets", StyleType.WHITE_BRACKETS, conv.toWhiteBrackets(previewText)),
            UnicodeStyleModel("Tortoise Brackets", StyleType.TORTOISE_BRACKETS, conv.toTortoiseBrackets(previewText)),
            UnicodeStyleModel("Angle Brackets", StyleType.ANGLE_BRACKETS, conv.toAngleBrackets(previewText)),
            UnicodeStyleModel("Double Angle", StyleType.DOUBLE_ANGLE_BRACKETS, conv.toDoubleAngleBrackets(previewText)),
            UnicodeStyleModel("Corner Brackets", StyleType.CORNER_BRACKETS, conv.toCornerBrackets(previewText)),
            UnicodeStyleModel("Floor Brackets", StyleType.FLOOR_BRACKETS, conv.toFloorBrackets(previewText)),
            UnicodeStyleModel("Parentheses", StyleType.PARENTHESES, conv.toParentheses(previewText)),
            UnicodeStyleModel("Square Parentheses", StyleType.SQUARE_PARENTHESES, conv.toSquareParentheses(previewText)),
            UnicodeStyleModel("Curly Parentheses", StyleType.CURLY_PARENTHESES, conv.toCurlyParentheses(previewText)),
            UnicodeStyleModel("Arrow Brackets", StyleType.ARROW_BRACKETS, conv.toArrowBrackets(previewText)),
            UnicodeStyleModel("Quotation Marks", StyleType.QUOTATION_MARKS, conv.toQuotationMarks(previewText)),
            UnicodeStyleModel("Single Quotes", StyleType.SINGLE_QUOTES, conv.toSingleQuotes(previewText)),

            // Emoji Decorations (5)
            UnicodeStyleModel("Stars", StyleType.STARS, conv.toStars(previewText)),
            UnicodeStyleModel("Hearts", StyleType.HEARTS, conv.toHearts(previewText)),
            UnicodeStyleModel("Sparkles", StyleType.SPARKLES, conv.toSparkles(previewText)),
            UnicodeStyleModel("Crown", StyleType.CROWN, conv.toCrown(previewText)),
            UnicodeStyleModel("Flowers", StyleType.FLOWERS, conv.toFlowers(previewText)),

            // Arrow Decorations (5)
            UnicodeStyleModel("Arrows Left", StyleType.ARROWS_LEFT, conv.toArrowsLeft(previewText)),
            UnicodeStyleModel("Arrows Right", StyleType.ARROWS_RIGHT, conv.toArrowsRight(previewText)),
            UnicodeStyleModel("Arrows Both", StyleType.ARROWS_BOTH, conv.toArrowsBoth(previewText)),
            UnicodeStyleModel("Double Arrows", StyleType.DOUBLE_ARROWS, conv.toDoubleArrows(previewText)),
            UnicodeStyleModel("Triangle Arrows", StyleType.TRIANGLE_ARROWS, conv.toTriangleArrows(previewText)),

            // Box Drawing (4)
            UnicodeStyleModel("Box Single", StyleType.BOX_SINGLE, conv.toBoxSingle(previewText)),
            UnicodeStyleModel("Box Double", StyleType.BOX_DOUBLE, conv.toBoxDouble(previewText)),
            UnicodeStyleModel("Box Rounded", StyleType.BOX_ROUNDED, conv.toBoxRounded(previewText)),
            UnicodeStyleModel("Box Heavy", StyleType.BOX_HEAVY, conv.toBoxHeavy(previewText)),

            // Block Backgrounds (4)
            UnicodeStyleModel("Block Light", StyleType.BLOCK_LIGHT, conv.toBlockLight(previewText)),
            UnicodeStyleModel("Block Medium", StyleType.BLOCK_MEDIUM, conv.toBlockMedium(previewText)),
            UnicodeStyleModel("Block Heavy", StyleType.BLOCK_HEAVY, conv.toBlockHeavy(previewText)),
            UnicodeStyleModel("Block Full", StyleType.BLOCK_FULL, conv.toBlockFull(previewText)),

            // Character Substitution / Lookalike (7)
            UnicodeStyleModel("Cyrillic Look", StyleType.CYRILLIC_LOOKALIKE, conv.toCyrillicLookalike(previewText)),
            UnicodeStyleModel("Greek Look", StyleType.GREEK_LOOKALIKE, conv.toGreekLookalike(previewText)),
            UnicodeStyleModel("Asian Mix", StyleType.ASIAN_MIX, conv.toAsianMix(previewText)),
            UnicodeStyleModel("Math Symbols", StyleType.MATH_SYMBOLS, conv.toMathSymbols(previewText)),
            UnicodeStyleModel("Weird Mix", StyleType.WEIRD_MIX, conv.toWeirdMix(previewText)),
            UnicodeStyleModel("Squared Neg", StyleType.SQUARED_NEGATIVE_LOOKALIKE, conv.toSquaredNegativeLookalike(previewText)),
            UnicodeStyleModel("Currency Mix", StyleType.CURRENCY_MIX, conv.toCurrencyMix(previewText)),

            // Mixed / Random Styles (13)
            UnicodeStyleModel("Mixed Light", StyleType.MIXED_LIGHT, conv.toMixedLight(previewText)),
            UnicodeStyleModel("Mixed Medium", StyleType.MIXED_MEDIUM, conv.toMixedMedium(previewText)),
            UnicodeStyleModel("Mixed Heavy", StyleType.MIXED_HEAVY, conv.toMixedHeavy(previewText)),
            UnicodeStyleModel("aLtErNaTiNg", StyleType.ALTERNATING_CASE, conv.toAlternatingCase(previewText)),
            UnicodeStyleModel("RaNdOm CaSe", StyleType.RANDOM_CASE, conv.toRandomCase(previewText)),
            UnicodeStyleModel("Mixed Sub", StyleType.MIXED_SUBSTITUTION, conv.toMixedSubstitution(previewText)),
            UnicodeStyleModel("Alt Styles", StyleType.ALTERNATING_STYLES, conv.toAlternatingStyles(previewText)),
            UnicodeStyleModel("Wave Style", StyleType.WAVE_STYLE, conv.toWaveStyle(previewText)),
            UnicodeStyleModel("Bubble Text", StyleType.BUBBLE_TEXT, conv.toBubbleText(previewText)),
            UnicodeStyleModel("Fancy Mix", StyleType.FANCY_MIX, conv.toFancyMix(previewText)),
            UnicodeStyleModel("Aesthetic", StyleType.AESTHETIC, conv.toAesthetic(previewText)),
            UnicodeStyleModel("Vaporwave", StyleType.VAPORWAVE, conv.toVaporwave(previewText)),
            UnicodeStyleModel("Glitch Mix", StyleType.GLITCH_MIX, conv.toGlitchMix(previewText)),
            UnicodeStyleModel("Crazy Mix", StyleType.CRAZY_MIX, conv.toCrazyMix(previewText)),

            // NEW: Mixed Script Styles (15)
            UnicodeStyleModel("Armenian", StyleType.ARMENIAN, conv.toArmenian(previewText)),
            UnicodeStyleModel("Thai-Lao", StyleType.THAI_LAO_MIX, conv.toThaiLaoMix(previewText)),
            UnicodeStyleModel("Japanese", StyleType.JAPANESE_MIX, conv.toJapaneseMix(previewText)),
            UnicodeStyleModel("Medieval", StyleType.MEDIEVAL_LATIN, conv.toMedievalLatin(previewText)),
            UnicodeStyleModel("Bopomofo CJK", StyleType.BOPOMOFO_CJK, conv.toBopomofoCJK(previewText)),
            UnicodeStyleModel("Yi", StyleType.YI_SYLLABLES, conv.toYiSyllables(previewText)),
            UnicodeStyleModel("Canadian", StyleType.CANADIAN_ABORIGINAL, conv.toCanadianAboriginal(previewText)),
            UnicodeStyleModel("Cherokee", StyleType.CHEROKEE, conv.toCherokee(previewText)),
            UnicodeStyleModel("Lisu", StyleType.LISU, conv.toLisu(previewText)),
            UnicodeStyleModel("Hebrew-Greek", StyleType.HEBREW_GREEK_MIX, conv.toHebrewGreekMix(previewText)),
            UnicodeStyleModel("Gujarati", StyleType.GUJARATI_MIX, conv.toGujaratiMix(previewText)),
            UnicodeStyleModel("Thai-Armenian", StyleType.THAI_ARMENIAN_MIX, conv.toThaiArmenianMix(previewText)),
            UnicodeStyleModel("Cyrillic-Armenian", StyleType.CYRILLIC_ARMENIAN_MIX, conv.toCyrillicArmenianMix(previewText)),
            UnicodeStyleModel("Greek-Cyrillic", StyleType.GREEK_CYRILLIC_MIX, conv.toGreekCyrillicMix(previewText)),
            UnicodeStyleModel("Latin-Greek-VN", StyleType.LATIN_GREEK_VIETNAMESE_MIX, conv.toLatinGreekVietnameseMix(previewText)),

            // IPA & Phonetic (3)
            UnicodeStyleModel("IPA Phonetic", StyleType.IPA_PHONETIC, conv.toIPAPhonetic(previewText)),
            UnicodeStyleModel("IPA Extended", StyleType.IPA_EXTENDED, conv.toIPAExtended(previewText)),
            UnicodeStyleModel("Phonetic Ext", StyleType.PHONETIC_EXTENSIONS, conv.toPhoneticExtensions(previewText)),

            // Advanced Greek (3)
            UnicodeStyleModel("Greek Extended", StyleType.GREEK_EXTENDED, conv.toGreekExtended(previewText)),
            UnicodeStyleModel("Greek Coptic", StyleType.GREEK_COPTIC_MIX, conv.toGreekCopticMix(previewText)),
            UnicodeStyleModel("Greek Archaic", StyleType.GREEK_ARCHAIC, conv.toGreekArchaic(previewText)),

            // Symbol Separators (6)
            UnicodeStyleModel("Sep N-Ary", StyleType.SEPARATOR_N_ARY, conv.toSeparatorNAry(previewText)),
            UnicodeStyleModel("Sep APL", StyleType.SEPARATOR_APL, conv.toSeparatorAPL(previewText)),
            UnicodeStyleModel("Sep Star", StyleType.SEPARATOR_STAR, conv.toSeparatorStar(previewText)),
            UnicodeStyleModel("Sep Dot", StyleType.SEPARATOR_DOT, conv.toSeparatorDot(previewText)),
            UnicodeStyleModel("Sep Diamond", StyleType.SEPARATOR_DIAMOND, conv.toSeparatorDiamond(previewText)),
            UnicodeStyleModel("Sep Circle", StyleType.SEPARATOR_CIRCLE, conv.toSeparatorCircle(previewText)),

            // Complex Combining (8)
            UnicodeStyleModel("Bridge Mark", StyleType.COMBINING_INVERTED_BRIDGE, conv.toCombiningInvertedBridge(previewText)),
            UnicodeStyleModel("Candrabindu", StyleType.COMBINING_CANDRABINDU, conv.toCombiningCandrabindu(previewText)),
            UnicodeStyleModel("Zigzag", StyleType.COMBINING_ZIGZAG, conv.toCombiningZigzag(previewText)),
            UnicodeStyleModel("Arrow Mark", StyleType.COMBINING_ARROW, conv.toCombiningArrow(previewText)),
            UnicodeStyleModel("Multi Diacritics", StyleType.DOUBLE_STRUCK_MULTI_DIACRITICS, conv.toDoubleStruckMultiDiacritics(previewText)),
            UnicodeStyleModel("Random Heavy", StyleType.DIACRITICS_RANDOM_HEAVY, conv.toDiacriticsRandomHeavy(previewText)),
            UnicodeStyleModel("Super + Dia", StyleType.SUPERSCRIPT_DIACRITICS_MIX, conv.toSuperscriptDiacriticsMix(previewText)),
            UnicodeStyleModel("Sub + Dia", StyleType.SUBSCRIPT_DIACRITICS_MIX, conv.toSubscriptDiacriticsMix(previewText)),

            // Mirrored (2)
            UnicodeStyleModel("Mirrored Rev", StyleType.MIRRORED_REVERSED, conv.toMirroredReversed(previewText)),
            UnicodeStyleModel("Upside Mirror", StyleType.UPSIDE_DOWN_MIRRORED, conv.toUpsideDownMirrored(previewText)),

            // Heavy Effects (3)
            UnicodeStyleModel("Zalgo Arrows", StyleType.ZALGO_ARROWS, conv.toZalgoArrows(previewText)),
            UnicodeStyleModel("Glitch Heavy", StyleType.GLITCH_HEAVY_MARKS, conv.toGlitchHeavyMarks(previewText)),
            UnicodeStyleModel("Chaotic", StyleType.CHAOTIC_MIX, conv.toChaoticMix(previewText))
        )

        if (adapter == null) {
            adapter = UnicodeStyleAdapter { styleType ->
                onStyleSelected?.invoke(styleType)
            }

            val spanCount = 3
            val spacing = 16
            val includeEdge = true

            binding.rvFonts.apply {
                layoutManager = GridLayoutManager(requireContext(), spanCount)
                addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
                this.adapter = this@UnicodeStyleFragment.adapter
            }
        }

        adapter?.submitList(styles)
    }

    private fun refreshStylesList() {
        if (_binding != null) {
            setupRecyclerView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }
}
