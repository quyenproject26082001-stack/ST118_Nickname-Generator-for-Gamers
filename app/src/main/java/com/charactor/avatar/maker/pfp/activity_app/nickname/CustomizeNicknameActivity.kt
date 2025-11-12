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
    private var currentUnicodeStyle: StyleType? = null

    // Reference to UnicodeStyleFragment for real-time updates
    private var unicodeStyleFragment: UnicodeStyleFragment? = null

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
                1 -> "Unicode Styles"
                2 -> "Right Symbol"
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
        if (currentUnicodeStyle != null) {
            baseText = CustomizeNicknameActivityStyleApplier.applyUnicodeStyle(baseText, currentUnicodeStyle!!)
        }

        // Always use Roboto Regular font
        binding.tvPreview.typeface = androidx.core.content.res.ResourcesCompat.getFont(this, R.font.roboto_regular)
        binding.tvPreview.text = baseText
        binding.tvLength.text = baseText.length.toString()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("nickname", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
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
                // Save state for undo/redo
                saveState()
                updateUndoRedoButtons()
                dialog.dismiss()
                Toast.makeText(this, "Name updated", Toast.LENGTH_SHORT).show()
            } else {
                // Restore to original name when text is empty
                inputName = originalName
                updatePreview()
                unicodeStyleFragment?.updatePreviewText(originalName)
                // Save state for undo/redo
                saveState()
                updateUndoRedoButtons()
                dialog.dismiss()
                Toast.makeText(this, "Name restored to original", Toast.LENGTH_SHORT).show()
            }
        }

        // Set dialog size - Add this code
        dialog.show()


    }
}

