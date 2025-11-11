package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.charactor.avatar.maker.pfp.databinding.FragmentStyleTextBinding

class StyleTextFragment : Fragment() {
    
    private var _binding: FragmentStyleTextBinding? = null
    private val binding get() = _binding!!
    
    private var onFontSelected: ((String) -> Unit)? = null
    
    companion object {
        fun newInstance(onFontSelected: (String) -> Unit): StyleTextFragment {
            return StyleTextFragment().apply {
                this.onFontSelected = onFontSelected
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
        setupRecyclerView()
    }
    
    private fun setupRecyclerView() {
        val fonts = listOf(
            FontModel("Roboto Regular", "roboto_regular"),
            FontModel("Roboto Bold", "roboto_bold"),
            FontModel("Roboto Italic", "roboto_italic"),
            FontModel("Roboto Medium", "roboto_medium"),
            FontModel("Sigmar", "sigmar_regular"),
            FontModel("Londrina Solid", "londrina_solid_regular"),
            FontModel("Montserrat Bold", "montserrat_bold"),
            FontModel("Montserrat Italic", "montserrat_italic"),
            FontModel("Toruksc", "toruksc_regular")
        )
        
        val adapter = FontAdapter { fontFamily ->
            onFontSelected?.invoke(fontFamily)
        }
        
        binding.rvFonts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        
        adapter.submitList(fonts)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

