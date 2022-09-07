package website.italojar.klikincommerces.utils

import website.italojar.klikincommerces.databinding.ItemCategoriesBinding

fun ItemCategoriesBinding.modifyCategory(image: Int, name: String, color: Int) {
    this.imCategory.setImageResource(image)
    this.tvCategory.text = name
    this.tvCategory.setTextColor(this.root.context.getColor(color))
}