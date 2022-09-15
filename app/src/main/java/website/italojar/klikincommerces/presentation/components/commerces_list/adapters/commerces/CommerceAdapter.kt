package website.italojar.klikincommerces.presentation.components.commerces_list.adapters.commerces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.presentation.model.CommerceVO

class CommerceAdapter(
    private val commerces: List<CommerceVO>,
    private val onClick: (commerce: CommerceVO) -> Unit
) : RecyclerView.Adapter<CommerceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommerceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_commerce, parent, false)
        return CommerceViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommerceViewHolder, position: Int) {
        holder.render(commerces[position])
        holder.itemView.setOnClickListener { onClick(commerces[position]) }
    }

    override fun getItemCount() = commerces.size
}