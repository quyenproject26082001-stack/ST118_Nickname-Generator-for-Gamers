package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.charactor.avatar.maker.pfp.databinding.FragmentSymbolBinding

class SymbolFragment : Fragment() {

    private var _binding: FragmentSymbolBinding? = null
    private val binding get() = _binding!!

    private var isLeftSymbol: Boolean = true
    private var onSymbolSelected: ((String) -> Unit)? = null
    private var adapter: SymbolAdapter? = null
    
    companion object {
        private const val ARG_IS_LEFT = "is_left"
        
        fun newInstance(isLeft: Boolean, onSymbolSelected: (String) -> Unit): SymbolFragment {
            return SymbolFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_IS_LEFT, isLeft)
                }
                this.onSymbolSelected = onSymbolSelected
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLeftSymbol = arguments?.getBoolean(ARG_IS_LEFT) ?: true
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymbolBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }
    
    private fun setupRecyclerView() {
        // Use centralized symbol constants for consistency across all screens
        val symbols = SymbolConstants.ALL_EMOJIS

        adapter = SymbolAdapter(symbols) { symbol ->
            onSymbolSelected?.invoke(symbol)
        }

        val spanCount = 3
        val includeEdge = true

        binding.rvSymbols.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, 0, includeEdge))
            setHasFixedSize(true) // Optimization: Skip measure when items change
            setItemViewCacheSize(20) // Optimization: Cache more views for smooth scroll
            this.adapter = this@SymbolFragment.adapter
        }

        adapter?.submitList(symbols)
    }

    fun setInitialSelection(symbol: String) {
        adapter?.setInitialSelection(symbol)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

