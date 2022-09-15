package website.italojar.klikincommerces.presentation.components.commerces_list.adapters.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ItemCategoriesBinding
import website.italojar.klikincommerces.utils.modifyCategory

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoriesBinding.bind(view)
    fun render(category: String?) {
        if (category == null) return
        when (category) {
            "SHOPPING" -> {
                binding.modifyCategory(
                    R.drawable.cart_colour, "Compras", R.color.category_shopping
                )
            }
            "FOOD" -> {
                binding.modifyCategory(
                    R.drawable.catering_colour, "Restaurantes", R.color.primary_light
                )
            }
            "BEAUTY" -> {
                binding.modifyCategory(
                    R.drawable.beauty_colour, "Belleza", R.color.category_beauty
                )
            }
            "LEISURE" -> {
                binding.modifyCategory(
                    R.drawable.leisure_colour, "Ocio", R.color.category_leisure
                )
            }
            "OTHER" -> {
                binding.modifyCategory(
                    R.drawable.truck_colour, "Otros", R.color.category_others
                )
            }
        }
    }
}
