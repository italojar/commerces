package website.italojar.klikincommerces.presentation.components.commerces_list.adapters.commerces

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ItemCommerceBinding
import website.italojar.klikincommerces.presentation.model.CommerceVO
import website.italojar.klikincommerces.utils.loadImage

class CommerceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCommerceBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(commerce: CommerceVO) {
        binding.tvTitle.text = commerce.name
        binding.tvSubtitle.text = commerce.openingHours
        if (commerce.distance != null) {
            binding.tvDistace.text = "${commerce.distance} km."
        }
        binding.imCommerce.loadImage(
            commerce.logo?.thumbnails?.small
                ?: "https://atenoil.com/wp-content/uploads/2018/07/6-min-1.jpg"
        )

        when (commerce.category) {
            "SHOPPING" -> {
                binding.viewCategoryColor.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.category_shopping
                    )
                )
                binding.imCategory.setImageResource(R.drawable.cart_white)
            }
            "FOOD" -> {
                binding.viewCategoryColor.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.primary_light
                    )
                )
                binding.imCategory.setImageResource(R.drawable.catering_white)
            }
            "BEAUTY" -> {
                binding.viewCategoryColor.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.category_beauty
                    )
                )
                binding.imCategory.setImageResource(R.drawable.beauty_white)
            }
            "LEISURE" -> {
                binding.viewCategoryColor.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.category_leisure
                    )
                )
                binding.imCategory.setImageResource(R.drawable.leisure_white)
            }
            "OTHER" -> {
                binding.viewCategoryColor.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.category_others
                    )
                )
                binding.imCategory.setImageResource(R.drawable.truck_white)
            }
        }
    }
}