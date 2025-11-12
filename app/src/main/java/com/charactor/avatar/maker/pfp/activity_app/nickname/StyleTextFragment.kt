package com.charactor.avatar.maker.pfp.activity_app.nickname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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
            FontModel("Roboto Extra Bold", "roboto_extra_bold"),
            FontModel("Roboto Light Italic", "roboto_light_italic"),
            FontModel("Roboto Medium Italic", "roboto_medium_italic"),
            FontModel("Montserrat Bold", "montserrat_bold"),
            FontModel("Montserrat Italic", "montserrat_italic"),
            FontModel("Montserrat Medium", "montserrat_medium"),
            FontModel("Sigmar", "sigmar_regular"),
            FontModel("Londrina Solid", "londrina_solid_regular"),
            FontModel("Toruksc", "toruksc_regular"),
            FontModel("Aeogo Pixellated", "aeogopixellated_regular"),
            FontModel("Alfinstare", "alfinstare_qobylatin_demo_version"),
            FontModel("Alfinstare Italic", "alfinstare_qobylatin_italic_demo_version"),
            FontModel("Alivia", "alivia"),
            FontModel("Animal Chariot", "animal_chariot"),
            FontModel("Ape Shit", "ape_shit_"),
            FontModel("Audiencia", "audiencia_portuguesa"),
            FontModel("Augusthin", "augusthin_beatrice_demo_version"),
            FontModel("Augusthin Italic", "augusthin_beatrice_italic_demo_version"),
            FontModel("Author Handwriting", "author_handwriting"),
            FontModel("Balboa Magic", "balboa_magic"),
            FontModel("Bemand", "bemand"),
            FontModel("Bergstena Decorated", "bergstenadecorated_personal_use_only"),
            FontModel("Bergstena Script", "bergstenascript_personal_use_only"),
            FontModel("Bergstena Swashed", "bergstenaswashed_personal_use_only"),
            FontModel("Birthday", "birthday"),
            FontModel("Bladge", "bladge"),
            FontModel("Blastge", "blastge_demo_version"),
            FontModel("Blastge Italic", "blastge_italic_demo_version"),
            FontModel("Blood Victim", "blood_victim_zombie"),
            FontModel("Bloody Leader", "bloody_leader"),
            FontModel("Blue Shine", "blue_shine"),
            FontModel("Breakside", "breakside"),
            FontModel("Breath River", "breath_of_the_river"),
            FontModel("Bubble Heart", "bubble_heart_monogram"),
            FontModel("Calesta", "calesta"),
            FontModel("Chinese Circles", "chinese_circles"),
            FontModel("Chiringuito", "chiringuito_espacial"),
            FontModel("Classic Stroke", "classicstroke"),
            FontModel("Classic Texture", "classicstroke_texture"),
            FontModel("Coco Chamel", "coco_chamel"),
            FontModel("Cozy Autumns", "cozy_autumns"),
            FontModel("Cracked Concrete", "crackedconcrete"),
            FontModel("Cracked Bold", "crackedconcrete_bold"),
            FontModel("Cracked Bold Display", "crackedconcrete_bolddisplay"),
            FontModel("Cracked Bold Display Rough", "crackedconcrete_bolddisplayrough"),
            FontModel("Cracked Bold Rough", "crackedconcrete_boldrough"),
            FontModel("Cracked Display", "crackedconcrete_display"),
            FontModel("Cracked Display Rough", "crackedconcrete_displayrough"),
            FontModel("Cracked Rough", "crackedconcrete_rough"),
            FontModel("Crafter Signature", "crafter_signature_serif"),
            FontModel("Crazy Blocks", "crazy_blocks"),
            FontModel("Creaking Crypt", "creakingcrypt_regular"),
            FontModel("Death House", "death_house_halloween"),
            FontModel("Death Stars", "death_stars"),
            FontModel("Demon Hunter", "demon_hunter"),
            FontModel("Doyotama", "doyotama_personal_use"),
            FontModel("Ethnic", "ethnic"),
            FontModel("Feeling Grateful", "feeling_grateful_demo"),
            FontModel("Fiance Rosalie", "fiance_rosalie"),
            FontModel("Flower Garden", "flower_garden"),
            FontModel("Fortune Brother", "fortune_brother"),
            FontModel("Garaven", "garaven"),
            FontModel("Garaven Outline", "garaven_outline"),
            FontModel("Gear6", "gear6"),
            FontModel("Gear6 3D", "gear6_3d"),
            FontModel("Gear6 Black", "gear6_black"),
            FontModel("Gear6 Clean", "gear6_clean"),
            FontModel("Gear6 Elements", "gear6_elements"),
            FontModel("Gear6 Frame", "gear6_frame"),
            FontModel("Gear6 Gradient", "gear6_gradient"),
            FontModel("Gear6 Line", "gear6_line"),
            FontModel("Gear6 Outline", "gear6_outline"),
            FontModel("Gear6 Rever", "gear6_rever"),
            FontModel("Gear6 Reverlight", "gear6_reverlight"),
            FontModel("Gear6 Shade", "gear6_shade"),
            FontModel("Gear6 Shine", "gear6_shine"),
            FontModel("Gear6 Spark", "gear6_spark"),
            FontModel("Gear6 White", "gear6_white"),
            FontModel("Gear6 White Grad", "gear6_whitegrad"),
            FontModel("Gear6 White Line", "gear6_whiteline"),
            FontModel("Gear6 White Shine", "gear6_whiteshine"),
            FontModel("Gemola", "gemola_ttf"),
            FontModel("Ghost Blaze", "ghost_blaze_ttf_demo"),
            FontModel("Ghost Hunters", "ghost_hunters"),
            FontModel("Ghost Pumpkin", "ghost_pumpkin"),
            FontModel("Graff Punks", "graff_punks_personal_use"),
            FontModel("Halloween Decorative", "halloween_decorative_demo"),
            FontModel("Halloween Time", "halloween_time"),
            FontModel("Halloween Italic", "halloween_time_italic"),
            FontModel("Hentai Universe", "hentai_universe"),
            FontModel("Hold Hand", "holdhand"),
            FontModel("Hold Hand Display", "holdhand_display"),
            FontModel("Hold Hand Display Rough", "holdhand_displayrough"),
            FontModel("Hold Hand Rough", "holdhand_rough"),
            FontModel("Home Office", "home_office"),
            FontModel("Jifer Bogy", "jifer_bogy_trial"),
            FontModel("Johar", "jjohar"),
            FontModel("Jumpis Graffiti", "jumpisgraffiti_regular"),
            FontModel("Jumpis Outline", "jumpisgraffiti_outline"),
            FontModel("Life Changer", "life_changer_demo"),
            FontModel("Lovanaru", "lovanaru"),
            FontModel("Macabre Night", "macabre_night"),
            FontModel("Metal Old", "metalold"),
            FontModel("Milk Cake", "milk_cake"),
            FontModel("Mindway", "mindway"),
            FontModel("Miniday", "miniday"),
            FontModel("Monlight", "monlight"),
            FontModel("Monster Phantom", "monster_phantom_ttf_demo"),
            FontModel("Monster Spooky", "monsterspooky_regular"),
            FontModel("Motterdam", "motterdam"),
            FontModel("Music Volume", "music_volume_reg"),
            FontModel("Music Arch", "music_volume_arch"),
            FontModel("Music Bold", "music_volume_bold"),
            FontModel("Music Italic", "music_volume_italic"),
            FontModel("Music Outline", "music_volume_outline"),
            FontModel("Music Rounded", "music_volume_rounded"),
            FontModel("Music Thin", "music_volume_thin"),
            FontModel("NCL Glassdy", "nclglassdyovertyeg_regular"),
            FontModel("NCL Oesbeq", "ncloesbeqboopsy_demo"),
            FontModel("NCL Wittsy", "nclwittsybubry_regular"),
            FontModel("Nordminne Script", "nordminnescript_personal_use_only"),
            FontModel("Organdy", "organdy"),
            FontModel("Outline Style", "outline_style"),
            FontModel("Papercute", "papercute"),
            FontModel("Peace Man", "peace_man"),
            FontModel("Psycho Waller", "psycho_waller"),
            FontModel("Rackety", "rackety_demo"),
            FontModel("Rosstanorla", "rosstanorla"),
            FontModel("Royal Calypso", "royal_calypso"),
            FontModel("Royal Mirror", "royal_mirror"),
            FontModel("Santa Horse", "santa_horse"),
            FontModel("Scary Vampire", "scary_vampire"),
            FontModel("Shadow Black", "shadow_black"),
            FontModel("Silent Witch", "silent_witch"),
            FontModel("Silk Rose", "silk_rose"),
            FontModel("Simple Krush", "simple_krush"),
            FontModel("Skyboxed Display", "skyboxed_display"),
            FontModel("Smoth Melon", "smoth_melon"),
            FontModel("Spooky Barbiey", "spooky_barbiey"),
            FontModel("Spray Typo Gang", "spray_typo_gang"),
            FontModel("Stockport Brush", "stockport___brush_personal"),
            FontModel("Stockport Italic", "stockport___italic_personal"),
            FontModel("Stockport Italic SS1", "stockport___italic_ss_1_personal"),
            FontModel("Stockport Italic SS2", "stockport___italic_ss_2_personal"),
            FontModel("Stockport Serif", "stockport___serif_personal_use"),
            FontModel("Stockport SS1", "stockport___ss_1_personal"),
            FontModel("Stockport SS2", "stockport___ss_2_personal"),
            FontModel("Styleturn", "styleturn_demo"),
            FontModel("Super Crash", "super_crash"),
            FontModel("Swash Break", "swash_break_trial"),
            FontModel("Swash Break SVG", "swash_break_trial_svg"),
            FontModel("Steel Tree", "the_steel_tree"),
            FontModel("Terakhir", "terakhirnewyear"),
            FontModel("Terror Scream", "terror_scream"),
            FontModel("Thrive", "thrive"),
            FontModel("Trembling Hands", "trembling_hands_demo"),
            FontModel("Unique Rose", "unique_rose"),
            FontModel("Witch Swirl", "witch_swirl_demo"),
            FontModel("With Logica", "withlogica")
        )

        val adapter = FontAdapter { fontFamily ->
            onFontSelected?.invoke(fontFamily)
        }

        val spanCount = 3
        val spacing = 16 // 16dp spacing between items
        val includeEdge = true

        binding.rvFonts.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
            this.adapter = adapter
        }

        adapter.submitList(fonts)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

