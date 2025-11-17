package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nicknamecreator.nicknamegenerator.namemaker.data.model.StyleType
import com.nicknamecreator.nicknamegenerator.namemaker.data.model.UnicodeStyleModel
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.FragmentStyleTextBinding

class UnicodeStyleFragment : Fragment() {

    private var _binding: FragmentStyleTextBinding? = null
    private val binding get() = _binding!!

    private var onStyleSelected: ((StyleType) -> Unit)? = null
    private var currentPreviewText: String = "Aa"
    private var adapter: UnicodeStyleAdapter? = null
    private var currentStyles: List<UnicodeStyleModel> = emptyList()

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

    fun setInitialSelection(styleType: StyleType) {
        adapter?.setInitialSelection(styleType)
    }

    fun clearSelection() {
        adapter?.clearSelection()
    }

    private fun setupRecyclerView() {
        // Use centralized style constants for consistency across all screens
        val styles = StyleConstants.generateStyleModels(currentPreviewText)

        // Update current styles
        currentStyles = styles

        val spanCount = 3
        val spacing = 16
        val includeEdge = true

        if (adapter == null) {
            adapter = UnicodeStyleAdapter(currentStyles) { styleType ->
                onStyleSelected?.invoke(styleType)
            }

            binding.rvFonts.apply {
                layoutManager = GridLayoutManager(requireContext(), spanCount)
                addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
                setHasFixedSize(true) // Optimization: Skip measure when items change
                setItemViewCacheSize(20) // Optimization: Cache more views for smooth scroll
                this.adapter = this@UnicodeStyleFragment.adapter
            }
        } else {
            // Recreate adapter with new styles when text changes
            adapter = UnicodeStyleAdapter(currentStyles) { styleType ->
                onStyleSelected?.invoke(styleType)
            }

            binding.rvFonts.adapter = adapter
        }

        // Set scroll callback for adapter - scroll to center vertical
        adapter?.onScrollToPosition = { position ->
            val layoutManager = binding.rvFonts.layoutManager as? GridLayoutManager
            layoutManager?.let {
                // Calculate the row of the item
                val row = position / spanCount
                // Calculate offset to center the item vertically
                val recyclerViewHeight = binding.rvFonts.height
                val itemHeight = if (binding.rvFonts.childCount > 0) {
                    binding.rvFonts.getChildAt(0).height
                } else {
                    200 // Default fallback
                }
                val offset = (recyclerViewHeight / 2) - (itemHeight / 2)

                // Scroll with offset
                it.scrollToPositionWithOffset(row * spanCount, offset)
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
