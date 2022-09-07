package website.italojar.klikincommerces.presentation.commerces_list.adapters.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ItemCategoriesBinding
import website.italojar.klikincommerces.utils.modifyCategory

class CategoriesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val categoryItem = ItemCategoriesBinding.bind(view)
    fun render(category: String?) {
        if (category == null) return
        when(category) {
            "SHOPPING" -> {
                categoryItem.modifyCategory(
                    R.drawable.cart_colour, "Compras", R.color.category_shopping)
            }
            "FOOD" -> {
                categoryItem.modifyCategory(
                    R.drawable.catering_colour, "Restaurantes", R.color.primary_light)
            }
            "BEAUTY" -> {
                categoryItem.modifyCategory(
                    R.drawable.beauty_colour, "Belleza", R.color.category_beauty)
            }
            "LEISURE" -> {
                categoryItem.modifyCategory(
                    R.drawable.leisure_colour, "Ocio", R.color.category_leisure)
            }
            "OTHER" -> {
                categoryItem.modifyCategory(
                    R.drawable.truck_colour, "Otros", R.color.category_others)
            }
        }
    }
}
