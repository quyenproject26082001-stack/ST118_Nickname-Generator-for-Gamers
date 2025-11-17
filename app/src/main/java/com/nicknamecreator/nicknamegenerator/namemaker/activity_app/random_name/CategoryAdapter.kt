package com.nicknamecreator.nicknamegenerator.namemaker.activity_app.random_name

import com.nicknamecreator.nicknamegenerator.namemaker.core.base.BaseAdapter
import com.nicknamecreator.nicknamegenerator.namemaker.core.extensions.setOnSingleClick
import com.nicknamecreator.nicknamegenerator.namemaker.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (CategoryModel) -> Unit
) : BaseAdapter<CategoryModel, ItemCategoryBinding>(ItemCategoryBinding::inflate) {

    private var selectedPosition = -1

    override fun onBind(binding: ItemCategoryBinding, item: CategoryModel, position: Int) {
        binding.tvCategoryName.text = item.name
        binding.ivCategoryIcon.setImageResource(item.iconRes)

        // Set selected state
        binding.ivCategoryIcon.isSelected = (position == selectedPosition)

        binding.ivCategoryIcon.setOnSingleClick {
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

