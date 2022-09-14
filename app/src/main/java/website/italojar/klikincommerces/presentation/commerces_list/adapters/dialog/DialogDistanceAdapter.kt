package website.italojar.klikincommerces.presentation.commerces_list.adapters.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import website.italojar.klikincommerces.R
import website.italojar.klikincommerces.databinding.ItemSelectDistanceBinding

class DialogDistanceAdapter(
    private val distances: List<String>,
    private val onClick: (distance: String) -> Unit
): RecyclerView.Adapter<DialogDistanceAdapter.DistanceeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistanceeViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_select_distance, parent, false)
        return DistanceeViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistanceeViewHolder, position: Int) {
        holder.render(distances[position])
        holder.itemView.setOnClickListener { onClick(distances[position]) }
    }

    override fun getItemCount() = distances.size

    inner class DistanceeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemSelectDistanceBinding.bind(view)
        fun render(distance: String) {
            binding.tvSelectDistance.text = distance
        }
    }
}