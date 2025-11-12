package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.activity_app.random_name.SaveSuccessActivity
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.gone
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityCustomizeNicknameBinding
import com.google.android.material.tabs.TabLayoutMediator

class CustomizeNicknameActivity : BaseActivity<ActivityCustomizeNicknameBinding>() {

    private var inputName: String = ""
    private var leftSymbol: String = ""
    private var rightSymbol: String = ""
    private var currentFont: String = "roboto_regular"
    private var currentUnicodeStyle: StyleType? = null
    private var useUnicodeStyle: Boolean = false

    // Reference to UnicodeStyleFragment for real-time updates
    private var unicodeStyleFragment: UnicodeStyleFragment? = null

    // History for undo/redo
    private val historyStack = mutableListOf<NicknameState>()
    private var currentHistoryIndex = -1

    data class NicknameState(
        val leftSymbol: String,
        val rightSymbol: String,
        val font: String,
        val unicodeStyle: StyleType?,
        val useUnicodeStyle: Boolean
    )

    override fun setViewBinding(): ActivityCustomizeNicknameBinding {
        return ActivityCustomizeNicknameBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Get input name from intent
        inputName = intent.getStringExtra("INPUT_NAME") ?: ""

        // Save initial state
        saveState()

        // Setup ViewPager with Fragments
        setupViewPager()

        // Update preview
        updatePreview()
        updateUndoRedoButtons()
    }

    override fun viewListener() {
        // Back button
        binding.btnBack.setOnSingleClick {
            onBackPressed()
        }

        // Undo button
        binding.btnUndo.setOnSingleClick {
            undo()
        }

        // Redo button
        binding.btnRedo.setOnSingleClick {
            redo()
        }

        // Save button
        binding.btnSave.setOnSingleClick {
            // Save text from preview (with Unicode style applied)
            val finalText = binding.tvPreview.text.toString()

            // Navigate to SaveSuccessActivity with the nickname
            startIntentRightToLeft(SaveSuccessActivity::class.java, "SAVED_NICKNAME", finalText)
        }

        // Copy button
        binding.btnCopy.setOnSingleClick {
            // Copy text from preview (with Unicode style applied)
            val finalText = binding.tvPreview.text.toString()
            copyToClipboard(finalText)
        }

        // Edit button
        binding.btnEdit.setOnSingleClick {
            showEditNameDialog()
        }
    }

    override fun initText() {
        // No additional text initialization needed
    }

    override fun initActionBar() {

        // Action bar already initialized in initView
    }

    private fun setupViewPager() {
        // Create and store reference to UnicodeStyleFragment
        unicodeStyleFragment = UnicodeStyleFragment.newInstance(inputName) { styleType ->
            currentUnicodeStyle = styleType
            useUnicodeStyle = true
            saveState()
            updatePreview()
            updateUndoRedoButtons()
        }

        val fragments = listOf<Fragment>(
            SymbolFragment.newInstance(true) { symbol ->
                leftSymbol = symbol
                saveState()
                updatePreview()
                updateUndoRedoButtons()
            },
            StyleTextFragment.newInstance { font ->
                currentFont = font
                useUnicodeStyle = false
                currentUnicodeStyle = null
                saveState()
                updatePreview()
                updateUndoRedoButtons()
            },
            unicodeStyleFragment!!,
            SymbolFragment.newInstance(false) { symbol ->
                rightSymbol = symbol
                saveState()
                updatePreview()
                updateUndoRedoButtons()
            }
        )

        val adapter = CustomizeViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Left Symbol"
                1 -> "Custom Fonts"
                2 -> "Unicode Styles"
                3 -> "Right Symbol"
                else -> ""
            }
        }.attach()
    }

    private fun updatePreview() {
        var baseText = buildString {
            if (leftSymbol.isNotEmpty()) append("$leftSymbol ")
            append(inputName)
            if (rightSymbol.isNotEmpty()) append(" $rightSymbol")
        }.trim()

        // Apply Unicode style if selected
        if (useUnicodeStyle && currentUnicodeStyle != null) {
            baseText = CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(baseText, currentUnicodeStyle!!)
            // Use default font for Unicode styles
            binding.tvPreview.typeface = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.roboto_regular)
        } else {
            // Apply custom font
            val fontResId = getFontResId(currentFont)
            binding.tvPreview.typeface = androidx.core.content.res.ResourcesCompat.getFont(this, fontResId)
        }

        binding.tvPreview.text = baseText
        binding.tvLength.text = baseText.length.toString()
    }

    private fun getFontResId(fontName: String): Int {
        // Keep original font mapping logic
        val fontResId = when (currentFont) {
            "roboto_bold" -> R.font.roboto_bold
            "roboto_italic" -> R.font.roboto_italic
            "roboto_medium" -> R.font.roboto_medium
            "roboto_extra_bold" -> R.font.roboto_extra_bold
            "roboto_light_italic" -> R.font.roboto_light_italic
            "roboto_medium_italic" -> R.font.roboto_medium_italic
            "montserrat_bold" -> R.font.montserrat_bold
            "montserrat_italic" -> R.font.montserrat_italic
            "montserrat_medium" -> R.font.montserrat_medium
            "sigmar_regular" -> R.font.sigmar_regular
            "londrina_solid_regular" -> R.font.londrina_solid_regular
            "toruksc_regular" -> R.font.toruksc_regular
            "aeogopixellated_regular" -> R.font.aeogopixellated_regular
            "alfinstare_qobylatin_demo_version" -> R.font.alfinstare_qobylatin_demo_version
            "alfinstare_qobylatin_italic_demo_version" -> R.font.alfinstare_qobylatin_italic_demo_version
            "alivia" -> R.font.alivia
            "animal_chariot" -> R.font.animal_chariot
            "ape_shit_" -> R.font.ape_shit_
            "audiencia_portuguesa" -> R.font.audiencia_portuguesa
            "augusthin_beatrice_demo_version" -> R.font.augusthin_beatrice_demo_version
            "augusthin_beatrice_italic_demo_version" -> R.font.augusthin_beatrice_italic_demo_version
            "author_handwriting" -> R.font.author_handwriting
            "balboa_magic" -> R.font.balboa_magic
            "bemand" -> R.font.bemand
            "bergstenadecorated_personal_use_only" -> R.font.bergstenadecorated_personal_use_only
            "bergstenascript_personal_use_only" -> R.font.bergstenascript_personal_use_only
            "bergstenaswashed_personal_use_only" -> R.font.bergstenaswashed_personal_use_only
            "birthday" -> R.font.birthday
            "bladge" -> R.font.bladge
            "blastge_demo_version" -> R.font.blastge_demo_version
            "blastge_italic_demo_version" -> R.font.blastge_italic_demo_version
            "blood_victim_zombie" -> R.font.blood_victim_zombie
            "bloody_leader" -> R.font.bloody_leader
            "blue_shine" -> R.font.blue_shine
            "breakside" -> R.font.breakside
            "breath_of_the_river" -> R.font.breath_of_the_river
            "bubble_heart_monogram" -> R.font.bubble_heart_monogram
            "calesta" -> R.font.calesta
            "chinese_circles" -> R.font.chinese_circles
            "chiringuito_espacial" -> R.font.chiringuito_espacial
            "classicstroke" -> R.font.classicstroke
            "classicstroke_texture" -> R.font.classicstroke_texture
            "coco_chamel" -> R.font.coco_chamel
            "cozy_autumns" -> R.font.cozy_autumns
            "crackedconcrete" -> R.font.crackedconcrete
            "crackedconcrete_bold" -> R.font.crackedconcrete_bold
            "crackedconcrete_bolddisplay" -> R.font.crackedconcrete_bolddisplay
            "crackedconcrete_bolddisplayrough" -> R.font.crackedconcrete_bolddisplayrough
            "crackedconcrete_boldrough" -> R.font.crackedconcrete_boldrough
            "crackedconcrete_display" -> R.font.crackedconcrete_display
            "crackedconcrete_displayrough" -> R.font.crackedconcrete_displayrough
            "crackedconcrete_rough" -> R.font.crackedconcrete_rough
            "crafter_signature_serif" -> R.font.crafter_signature_serif
            "crazy_blocks" -> R.font.crazy_blocks
            "creakingcrypt_regular" -> R.font.creakingcrypt_regular
            "death_house_halloween" -> R.font.death_house_halloween
            "death_stars" -> R.font.death_stars
            "demon_hunter" -> R.font.demon_hunter
            "doyotama_personal_use" -> R.font.doyotama_personal_use
            "ethnic" -> R.font.ethnic
            "feeling_grateful_demo" -> R.font.feeling_grateful_demo
            "fiance_rosalie" -> R.font.fiance_rosalie
            "flower_garden" -> R.font.flower_garden
            "fortune_brother" -> R.font.fortune_brother
            "garaven" -> R.font.garaven
            "garaven_outline" -> R.font.garaven_outline
            "gear6" -> R.font.gear6
            "gear6_3d" -> R.font.gear6_3d
            "gear6_black" -> R.font.gear6_black
            "gear6_clean" -> R.font.gear6_clean
            "gear6_elements" -> R.font.gear6_elements
            "gear6_frame" -> R.font.gear6_frame
            "gear6_gradient" -> R.font.gear6_gradient
            "gear6_line" -> R.font.gear6_line
            "gear6_outline" -> R.font.gear6_outline
            "gear6_rever" -> R.font.gear6_rever
            "gear6_reverlight" -> R.font.gear6_reverlight
            "gear6_shade" -> R.font.gear6_shade
            "gear6_shine" -> R.font.gear6_shine
            "gear6_spark" -> R.font.gear6_spark
            "gear6_white" -> R.font.gear6_white
            "gear6_whitegrad" -> R.font.gear6_whitegrad
            "gear6_whiteline" -> R.font.gear6_whiteline
            "gear6_whiteshine" -> R.font.gear6_whiteshine
            "gemola_ttf" -> R.font.gemola_ttf
            "ghost_blaze_ttf_demo" -> R.font.ghost_blaze_ttf_demo
            "ghost_hunters" -> R.font.ghost_hunters
            "ghost_pumpkin" -> R.font.ghost_pumpkin
            "graff_punks_personal_use" -> R.font.graff_punks_personal_use
            "halloween_decorative_demo" -> R.font.halloween_decorative_demo
            "halloween_time" -> R.font.halloween_time
            "halloween_time_italic" -> R.font.halloween_time_italic
            "hentai_universe" -> R.font.hentai_universe
            "holdhand" -> R.font.holdhand
            "holdhand_display" -> R.font.holdhand_display
            "holdhand_displayrough" -> R.font.holdhand_displayrough
            "holdhand_rough" -> R.font.holdhand_rough
            "home_office" -> R.font.home_office
            "jifer_bogy_trial" -> R.font.jifer_bogy_trial
            "jjohar" -> R.font.jjohar
            "jumpisgraffiti_regular" -> R.font.jumpisgraffiti_regular
            "jumpisgraffiti_outline" -> R.font.jumpisgraffiti_outline
            "life_changer_demo" -> R.font.life_changer_demo
            "lovanaru" -> R.font.lovanaru
            "macabre_night" -> R.font.macabre_night
            "metalold" -> R.font.metalold
            "milk_cake" -> R.font.milk_cake
            "mindway" -> R.font.mindway
            "miniday" -> R.font.miniday
            "monlight" -> R.font.monlight
            "monster_phantom_ttf_demo" -> R.font.monster_phantom_ttf_demo
            "monsterspooky_regular" -> R.font.monsterspooky_regular
            "motterdam" -> R.font.motterdam
            "music_volume_reg" -> R.font.music_volume_reg
            "music_volume_arch" -> R.font.music_volume_arch
            "music_volume_bold" -> R.font.music_volume_bold
            "music_volume_italic" -> R.font.music_volume_italic
            "music_volume_outline" -> R.font.music_volume_outline
            "music_volume_rounded" -> R.font.music_volume_rounded
            "music_volume_thin" -> R.font.music_volume_thin
            "nclglassdyovertyeg_regular" -> R.font.nclglassdyovertyeg_regular
            "ncloesbeqboopsy_demo" -> R.font.ncloesbeqboopsy_demo
            "nclwittsybubry_regular" -> R.font.nclwittsybubry_regular
            "nordminnescript_personal_use_only" -> R.font.nordminnescript_personal_use_only
            "organdy" -> R.font.organdy
            "outline_style" -> R.font.outline_style
            "papercute" -> R.font.papercute
            "peace_man" -> R.font.peace_man
            "psycho_waller" -> R.font.psycho_waller
            "rackety_demo" -> R.font.rackety_demo
            "rosstanorla" -> R.font.rosstanorla
            "royal_calypso" -> R.font.royal_calypso
            "royal_mirror" -> R.font.royal_mirror
            "santa_horse" -> R.font.santa_horse
            "scary_vampire" -> R.font.scary_vampire
            "shadow_black" -> R.font.shadow_black
            "silent_witch" -> R.font.silent_witch
            "silk_rose" -> R.font.silk_rose
            "simple_krush" -> R.font.simple_krush
            "skyboxed_display" -> R.font.skyboxed_display
            "smoth_melon" -> R.font.smoth_melon
            "spooky_barbiey" -> R.font.spooky_barbiey
            "spray_typo_gang" -> R.font.spray_typo_gang
            "stockport___brush_personal" -> R.font.stockport___brush_personal
            "stockport___italic_personal" -> R.font.stockport___italic_personal
            "stockport___italic_ss_1_personal" -> R.font.stockport___italic_ss_1_personal
            "stockport___italic_ss_2_personal" -> R.font.stockport___italic_ss_2_personal
            "stockport___serif_personal_use" -> R.font.stockport___serif_personal_use
            "stockport___ss_1_personal" -> R.font.stockport___ss_1_personal
            "stockport___ss_2_personal" -> R.font.stockport___ss_2_personal
            "styleturn_demo" -> R.font.styleturn_demo
            "super_crash" -> R.font.super_crash
            "swash_break_trial" -> R.font.swash_break_trial
            "swash_break_trial_svg" -> R.font.swash_break_trial_svg
            "the_steel_tree" -> R.font.the_steel_tree
            "terakhirnewyear" -> R.font.terakhirnewyear
            "terror_scream" -> R.font.terror_scream
            "thrive" -> R.font.thrive
            "trembling_hands_demo" -> R.font.trembling_hands_demo
            "unique_rose" -> R.font.unique_rose
            "witch_swirl_demo" -> R.font.witch_swirl_demo
            "withlogica" -> R.font.withlogica
            else -> R.font.roboto_regular
        }
        return fontResId
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("nickname", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun saveState() {
        val state = NicknameState(leftSymbol, rightSymbol, currentFont, currentUnicodeStyle, useUnicodeStyle)

        // Remove all states after current index
        if (currentHistoryIndex < historyStack.size - 1) {
            historyStack.subList(currentHistoryIndex + 1, historyStack.size).clear()
        }

        historyStack.add(state)
        currentHistoryIndex = historyStack.size - 1
    }

    private fun undo() {
        if (currentHistoryIndex > 0) {
            currentHistoryIndex--
            restoreState(historyStack[currentHistoryIndex])
            updateUndoRedoButtons()
        }
    }

    private fun redo() {
        if (currentHistoryIndex < historyStack.size - 1) {
            currentHistoryIndex++
            restoreState(historyStack[currentHistoryIndex])
            updateUndoRedoButtons()
        }
    }

    private fun restoreState(state: NicknameState) {
        leftSymbol = state.leftSymbol
        rightSymbol = state.rightSymbol
        currentFont = state.font
        currentUnicodeStyle = state.unicodeStyle
        useUnicodeStyle = state.useUnicodeStyle
        updatePreview()
    }

    private fun updateUndoRedoButtons() {
        if (currentHistoryIndex > 0) {
            binding.btnUndo.setColorFilter(ContextCompat.getColor(this, R.color.active_color))
            binding.btnUndo.isEnabled = true
        } else {
            binding.btnUndo.setColorFilter(ContextCompat.getColor(this, R.color.inactive_color))
            binding.btnUndo.isEnabled = false
        }

        // Redo button
        if (currentHistoryIndex < historyStack.size - 1) {
            binding.btnRedo.setColorFilter(ContextCompat.getColor(this, R.color.active_color))
            binding.btnRedo.isEnabled = true
        } else {
            binding.btnRedo.setColorFilter(ContextCompat.getColor(this, R.color.inactive_color))
            binding.btnRedo.isEnabled = false
        }
    }

    private fun showEditNameDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_edit_name)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set dialog width to match parent with horizontal margin 25dp
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels - (25 * 2 * displayMetrics.density).toInt()
        dialog.window?.setLayout(width, android.view.ViewGroup.LayoutParams.WRAP_CONTENT)

        val etEditName = dialog.findViewById<EditText>(R.id.etEditName)
        val btnUpdate = dialog.findViewById<AppCompatButton>(R.id.btnUpdate)

        // Set current name
        etEditName.setText(inputName)
        etEditName.setSelection(inputName.length)

        // Add TextWatcher for real-time preview update
        etEditName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update preview in real-time as user types
                inputName = s.toString()
                updatePreview()
                // Update Unicode style list items with new text
                unicodeStyleFragment?.updatePreviewText(inputName)
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })

        btnUpdate.setOnSingleClick {
            val newName = etEditName.text.toString().trim()
            if (newName.isNotEmpty()) {
                inputName = newName
                updatePreview()
                dialog.dismiss()
                Toast.makeText(this, "Name updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }

        // Set dialog size - Add this code
        dialog.show()


    }
}

