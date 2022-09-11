package website.italojar.klikincommerces.presentation.commerces_list.adapters.commerces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ItemCommerceBinding
import website.italojar.klikincommerces.presentation.model.CommerceVO
import website.italojar.klikincommerces.utils.loadImage

class CommerceAdapter(
    private val commerces: List<CommerceVO>,
    private val onClick: (commerce: CommerceVO) -> Unit
): RecyclerView.Adapter<CommerceAdapter.CommerceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommerceViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_commerce, parent, false)
        return CommerceViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommerceViewHolder, position: Int) {
        holder.render(commerces[position])
        holder.itemView.setOnClickListener { onClick(commerces[position]) }
    }

    override fun getItemCount() = commerces.size

    inner class CommerceViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCommerceBinding.bind(view)
        fun render(commerce: CommerceVO) {
            binding.tvTitle.text = commerce.name
            binding.tvSubtitle.text = commerce.openingHours
            binding.imCommerce.loadImage(
                commerce.logo?.thumbnails?.small ?: "https://atenoil.com/wp-content/uploads/2018/07/6-min-1.jpg"
            )
        }
    }
}