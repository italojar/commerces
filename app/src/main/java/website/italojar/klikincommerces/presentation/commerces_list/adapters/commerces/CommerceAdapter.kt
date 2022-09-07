package website.italojar.klikincommerces.presentation.commerces_list.adapters.commerces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.data.model.dto.CommerceDto
import website.italojar.klikincommerces.databinding.ItemCommerceBinding

class CommerceAdapter(
    private val commerces: List<CommerceDto>,
    private val onClick: (commerce: CommerceDto) -> Unit
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
        fun render(commerce: CommerceDto) {
            binding.tvTitle.text = commerce.name
            binding.tvSubtitle.text = commerce.description
        }
    }
}