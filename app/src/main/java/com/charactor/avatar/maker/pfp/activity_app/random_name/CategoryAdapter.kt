package com.charactor.avatar.maker.pfp.activity_app.random_name

import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (CategoryModel) -> Unit
) : BaseAdapter<CategoryModel, ItemCategoryBinding>(ItemCategoryBinding::inflate) {

    private var selectedPosition = -1

    override fun onBind(binding: ItemCategoryBinding, item: CategoryModel, position: Int) {
        binding.tvCategoryName.text = item.name
        binding.ivCategoryIcon.setImageResource(item.iconRes)

        // Set selected state
        binding.cardCategory.isSelected = (position == selectedPosition)

        binding.cardCategory.setOnSingleClick {
            val previousPosition = selectedPosition
            selectedPosition = position

            // Notify changes
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition)
            }
            notifyItemChanged(position)

            onCategoryClick(item)
        }
    }
}

