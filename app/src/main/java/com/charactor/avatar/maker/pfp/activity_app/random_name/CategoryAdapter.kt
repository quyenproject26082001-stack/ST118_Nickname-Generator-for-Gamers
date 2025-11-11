package com.charactor.avatar.maker.pfp.activity_app.random_name

import com.charactor.avatar.maker.pfp.core.base.BaseAdapter
import com.charactor.avatar.maker.pfp.core.extensions.setOnSingleClick
import com.charactor.avatar.maker.pfp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCategoryClick: (CategoryModel) -> Unit
) : BaseAdapter<CategoryModel, ItemCategoryBinding>(ItemCategoryBinding::inflate) {

    override fun onBind(binding: ItemCategoryBinding, item: CategoryModel, position: Int) {
        binding.tvCategoryName.text = item.name
        binding.ivCategoryIcon.setImageResource(item.iconRes)
        
        binding.cardCategory.setOnSingleClick {
            onCategoryClick(item)
        }
    }
}

