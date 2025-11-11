package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.charactor.avatar.maker.pfp.R
import com.charactor.avatar.maker.pfp.core.base.BaseActivity
import com.charactor.avatar.maker.pfp.core.extensions.gone
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ActivityCustomizeNicknameBinding
import com.google.android.material.tabs.TabLayoutMediator

class CustomizeNicknameActivity : BaseActivity<ActivityCustomizeNicknameBinding>() {

    private var inputName: String = ""
    private var leftSymbol: String = ""
    private var rightSymbol: String = ""
    private var currentFont: String = "roboto_regular"

    // History for undo/redo
    private val historyStack = mutableListOf<NicknameState>()
    private var currentHistoryIndex = -1

    data class NicknameState(
        val leftSymbol: String,
        val rightSymbol: String,
        val font: String
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
            val finalText = "$leftSymbol $inputName $rightSymbol".trim()
            Toast.makeText(this, "Saved: $finalText", Toast.LENGTH_SHORT).show()
        }
        
        // Copy button
        binding.btnCopy.setOnSingleClick {
            val finalText = "$leftSymbol $inputName $rightSymbol".trim()
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
        val fragments = listOf<Fragment>(
            SymbolFragment.newInstance(true) { symbol ->
                leftSymbol = symbol
                saveState()
                updatePreview()
                updateUndoRedoButtons()
            },
            StyleTextFragment.newInstance { font ->
                currentFont = font
                saveState()
                updatePreview()
                updateUndoRedoButtons()
            },
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
                1 -> "Style text"
                2 -> "Right Symbol"
                else -> ""
            }
        }.attach()
    }
    
    private fun updatePreview() {
        val finalText = buildString {
            if (leftSymbol.isNotEmpty()) append("$leftSymbol ")
            append(inputName)
            if (rightSymbol.isNotEmpty()) append(" $rightSymbol")
        }.trim()
        
        binding.tvPreview.text = finalText
        binding.tvLength.text = finalText.length.toString()
        
        // Apply font
        val fontResId = when (currentFont) {
            "sigmar_regular" -> R.font.sigmar_regular
            "londrina_solid_regular" -> R.font.londrina_solid_regular
            "montserrat_bold" -> R.font.montserrat_bold
            "montserrat_italic" -> R.font.montserrat_italic
            "roboto_bold" -> R.font.roboto_bold
            "roboto_italic" -> R.font.roboto_italic
            "roboto_medium" -> R.font.roboto_medium
            "toruksc_regular" -> R.font.toruksc_regular
            else -> R.font.roboto_regular
        }
        
        binding.tvPreview.typeface = androidx.core.content.res.ResourcesCompat.getFont(this, fontResId)
    }
    
    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("nickname", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun saveState() {
        val state = NicknameState(leftSymbol, rightSymbol, currentFont)

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

