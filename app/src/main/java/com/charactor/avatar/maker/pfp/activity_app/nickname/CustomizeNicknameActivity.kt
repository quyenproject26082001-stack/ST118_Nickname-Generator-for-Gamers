package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.activity_app.my_nickname.SavedNicknameModel
import com.charactor.avatar.maker.pfp.activity_app.random_name.SaveSuccessActivity
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.gone
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.core.extensions.startIntentRightToLeft
import com.charactor.avatar.maker.pfp.databinding.ActivityCustomizeNicknameBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomizeNicknameActivity : BaseActivity<ActivityCustomizeNicknameBinding>() {

    companion object {
        private const val TAG = "CustomizeNickname"
        private const val PREF_KEY_SAVED_NICKNAMES = "saved_nicknames"
    }

    private var inputName: String = ""
    private var leftSymbol: String = ""
    private var rightSymbol: String = ""
    private var currentUnicodeStyle: StyleType? = null

    // Reference to fragments for real-time updates and initial selection
    private var unicodeStyleFragment: UnicodeStyleFragment? = null
    private var leftSymbolFragment: SymbolFragment? = null
    private var rightSymbolFragment: SymbolFragment? = null

    // History for undo/redo
    private val historyStack = mutableListOf<NicknameState>()
    private var currentHistoryIndex = -1

    data class NicknameState(
        val inputName: String,
        val leftSymbol: String,
        val rightSymbol: String,
        val unicodeStyle: StyleType?
    )

    override fun setViewBinding(): ActivityCustomizeNicknameBinding {
        return ActivityCustomizeNicknameBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Get input name and metadata from intent
        inputName = intent.getStringExtra("INPUT_NAME") ?: ""
        leftSymbol = intent.getStringExtra("LEFT_SYMBOL") ?: ""
        rightSymbol = intent.getStringExtra("RIGHT_SYMBOL") ?: ""

        // Restore style type if provided
        val styleTypeName = intent.getStringExtra("STYLE_TYPE")
        currentUnicodeStyle = try {
            if (styleTypeName != null) StyleType.valueOf(styleTypeName) else null
        } catch (e: Exception) {
            null
        }

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

            // Check if nickname already exists on background thread
            lifecycleScope.launch {
                val isDuplicate = isNicknameDuplicate(finalText)

                if (isDuplicate) {
                    Toast.makeText(
                        this@CustomizeNicknameActivity,
                        getString(R.string.nickname_already_exists),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                // Navigate to SaveSuccessActivity with the nickname and metadata
                val intent = android.content.Intent(this@CustomizeNicknameActivity, SaveSuccessActivity::class.java)
                intent.putExtra("SAVED_NICKNAME", finalText)
                intent.putExtra("ORIGINAL_TEXT", inputName)
                intent.putExtra("LEFT_SYMBOL", leftSymbol)
                intent.putExtra("RIGHT_SYMBOL", rightSymbol)
                intent.putExtra("STYLE_TYPE", currentUnicodeStyle?.name)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
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
        // Create and store references to fragments
        leftSymbolFragment = SymbolFragment.newInstance(true) { symbol ->
            leftSymbol = symbol
            saveState()
            updatePreview()
            updateUndoRedoButtons()
        }

        unicodeStyleFragment = UnicodeStyleFragment.newInstance(inputName) { styleType ->
            currentUnicodeStyle = styleType
            saveState()
            updatePreview()
            updateUndoRedoButtons()
        }

        rightSymbolFragment = SymbolFragment.newInstance(false) { symbol ->
            rightSymbol = symbol
            saveState()
            updatePreview()
            updateUndoRedoButtons()
        }

        val fragments = listOf<Fragment>(
            leftSymbolFragment!!,
            unicodeStyleFragment!!,
            rightSymbolFragment!!
        )

        val adapter = CustomizeViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        // Preload all tabs to ensure they're ready for initial selection
        binding.viewPager.offscreenPageLimit = 2

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.left_symbol)
                1 -> getString(R.string.unicode_styles)
                2 -> getString(R.string.right_symbol)
                else -> ""
            }
        }.attach()

        // Set default tab to Unicode Styles (position 1)
        binding.viewPager.currentItem = 1

        // Set initial selections for restored state (with delay to ensure RecyclerViews are ready)
        binding.viewPager.postDelayed({
            if (leftSymbol.isNotEmpty()) {
                leftSymbolFragment?.setInitialSelection(leftSymbol)
            }
            if (rightSymbol.isNotEmpty()) {
                rightSymbolFragment?.setInitialSelection(rightSymbol)
            }
            if (currentUnicodeStyle != null) {
                unicodeStyleFragment?.setInitialSelection(currentUnicodeStyle!!)
            }
        }, 200) // 200ms delay to ensure all tabs are initialized (optimized from 300ms)
    }

    private fun updatePreview() {
        var baseText = buildString {
            if (leftSymbol.isNotEmpty()) append("$leftSymbol ")
            append(inputName)
            if (rightSymbol.isNotEmpty()) append(" $rightSymbol")
        }.trim()

        // Calculate length: inputName length + 1 for each symbol (to avoid counting multi-code-point emojis as 2+)
        var actualLength = inputName.length
        if (leftSymbol.isNotEmpty()) actualLength += 1
        if (rightSymbol.isNotEmpty()) actualLength += 1

        // Log length calculation details
        Log.d(TAG, "=== Length Calculation ===")
        Log.d(TAG, "Input Name: '$inputName' (length: ${inputName.length})")
        Log.d(TAG, "Left Symbol: '$leftSymbol' ${if (leftSymbol.isNotEmpty()) "(+1)" else ""}")
        Log.d(TAG, "Right Symbol: '$rightSymbol' ${if (rightSymbol.isNotEmpty()) "(+1)" else ""}")
        Log.d(TAG, "Base Text: '$baseText' (length: ${baseText.length})")
        Log.d(TAG, "Base Actual Length: $actualLength")

        // Add extra length from Unicode style decorations
        var extraLength = 0
        if (currentUnicodeStyle != null) {
            extraLength = getUnicodeStyleExtraLength(currentUnicodeStyle!!, baseText.length)
            actualLength += extraLength
            Log.d(TAG, "Unicode Style: ${currentUnicodeStyle!!.name}")
            Log.d(TAG, "Extra Length from Unicode Style: +$extraLength")
        } else {
            Log.d(TAG, "Unicode Style: NONE")
        }

        // Apply Unicode style if selected
        if (currentUnicodeStyle != null) {
            baseText = CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(baseText, currentUnicodeStyle!!)
        }

        Log.d(TAG, "Final Actual Length: $actualLength")
        Log.d(TAG, "Final Preview Text: '$baseText' (actual string length: ${baseText.length})")
        Log.d(TAG, "========================")

        // Always use Roboto Regular font
        binding.tvPreview.typeface = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.roboto_regular)
        binding.tvPreview.text = baseText
        binding.tvLength.text = actualLength.toString()

        // Enable marquee effect for auto-scrolling text
        binding.tvPreview.isSelected = true
    }

    private fun getUnicodeStyleExtraLength(styleType: StyleType, baseTextLength: Int): Int {
        return when (styleType) {
            // Decorative Brackets (+2: left + right bracket)
            StyleType.SQUARE_BRACKETS, StyleType.DOUBLE_BRACKETS, StyleType.CURLY_BRACKETS,
            StyleType.WHITE_BRACKETS, StyleType.TORTOISE_BRACKETS, StyleType.ANGLE_BRACKETS,
            StyleType.DOUBLE_ANGLE_BRACKETS, StyleType.CORNER_BRACKETS, StyleType.FLOOR_BRACKETS,
            StyleType.PARENTHESES, StyleType.SQUARE_PARENTHESES, StyleType.CURLY_PARENTHESES,
            StyleType.ARROW_BRACKETS, StyleType.QUOTATION_MARKS, StyleType.SINGLE_QUOTES -> 2

            // Emoji Decorations (+4: 2 emojis + 2 spaces)
            StyleType.STARS, StyleType.HEARTS, StyleType.SPARKLES,
            StyleType.CROWN, StyleType.FLOWERS -> 4

            // Arrow Decorations - One side (+2: arrow + space)
            StyleType.ARROWS_LEFT, StyleType.ARROWS_RIGHT -> 2

            // Arrow Decorations - Both sides (+4: 2 arrows + 2 spaces)
            StyleType.ARROWS_BOTH, StyleType.DOUBLE_ARROWS, StyleType.TRIANGLE_ARROWS -> 4

            // Box Drawing (+4: corners + lines)
            StyleType.BOX_SINGLE, StyleType.BOX_DOUBLE,
            StyleType.BOX_ROUNDED, StyleType.BOX_HEAVY -> 4

            // Block Backgrounds (+(N+1): N blocks + 1 space)
            StyleType.BLOCK_LIGHT, StyleType.BLOCK_MEDIUM,
            StyleType.BLOCK_HEAVY, StyleType.BLOCK_FULL -> baseTextLength + 1

            // Symbol Separators (+(N-1): separators between N characters)
            StyleType.SEPARATOR_N_ARY, StyleType.SEPARATOR_APL, StyleType.SEPARATOR_STAR,
            StyleType.SEPARATOR_DOT, StyleType.SEPARATOR_DIAMOND, StyleType.SEPARATOR_CIRCLE -> baseTextLength - 1

            // All other styles don't add extra characters, they just replace characters
            else -> 0
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("nickname", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }

    private fun saveState() {
        val state = NicknameState(inputName, leftSymbol, rightSymbol, currentUnicodeStyle)

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
        inputName = state.inputName
        leftSymbol = state.leftSymbol
        rightSymbol = state.rightSymbol
        currentUnicodeStyle = state.unicodeStyle
        updatePreview()
        // Update Unicode style fragment with restored name
        unicodeStyleFragment?.updatePreviewText(inputName)

        // Update selection for left symbol
        if (leftSymbol.isNotEmpty()) {
            leftSymbolFragment?.setInitialSelection(leftSymbol)
        }

        // Update selection for right symbol
        if (rightSymbol.isNotEmpty()) {
            rightSymbolFragment?.setInitialSelection(rightSymbol)
        }

        // Update selection for unicode style
        if (currentUnicodeStyle != null) {
            unicodeStyleFragment?.setInitialSelection(currentUnicodeStyle!!)
        }
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

    private suspend fun isNicknameDuplicate(nickname: String): Boolean = withContext(Dispatchers.IO) {
        val json = sharePreference.preferences.getString(PREF_KEY_SAVED_NICKNAMES, "[]")
        val type = object : TypeToken<List<SavedNicknameModel>>(){}.type
        val nicknames: List<SavedNicknameModel> = Gson().fromJson(json, type) ?: emptyList()

        return@withContext nicknames.any { it.nickname == nickname }
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

        // Save original name to restore if user deletes all text
        val originalName = inputName

        // Set current name
        etEditName.setText(inputName)
        etEditName.setSelection(inputName.length)

        btnUpdate.setOnSingleClick {
            val newName = etEditName.text.toString().trim()
            if (newName.isNotEmpty()) {
                inputName = newName
                updatePreview()
                // Update Unicode style list items with new text
                unicodeStyleFragment?.updatePreviewText(inputName)
                // Restore selection for unicode style
                if (currentUnicodeStyle != null) {
                    unicodeStyleFragment?.setInitialSelection(currentUnicodeStyle!!)
                }
                // Save state for undo/redo
                saveState()
                updateUndoRedoButtons()
                dialog.dismiss()
                Toast.makeText(this, getString(R.string.name_updated), Toast.LENGTH_SHORT).show()
            } else {
                // Restore to original name when text is empty
                inputName = originalName
                updatePreview()
                unicodeStyleFragment?.updatePreviewText(originalName)
                // Restore selection for unicode style
                if (currentUnicodeStyle != null) {
                    unicodeStyleFragment?.setInitialSelection(currentUnicodeStyle!!)
                }
                // Save state for undo/redo
                saveState()
                updateUndoRedoButtons()
                dialog.dismiss()
                Toast.makeText(this,
                    getString(R.string.name_restored_to_original), Toast.LENGTH_SHORT).show()
            }
        }

        // Set dialog size - Add this code
        dialog.show()


    }
}

