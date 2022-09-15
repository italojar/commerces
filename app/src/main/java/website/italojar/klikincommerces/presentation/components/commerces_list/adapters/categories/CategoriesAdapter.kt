package website.italojar.klikincommerces.presentation.components.commerces_list.adapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R

class CategoriesAdapter(
    private val categories: List<String>,
    private val onClick: (category: String?) -> Unit
) : RecyclerView.Adapter<CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_categories, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.render(categories[position])
        holder.itemView.setOnClickListener { onClick(categories[position]) }
    }

    override fun getItemCount() = categories.size
}