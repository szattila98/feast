package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ren.practice.core.domain.Ingredient
import ren.practice.feast.databinding.ItemIngredientRecordBinding

class IngredientAdapter(
    private val ingredients: MutableList<Ingredient>,
    private val longClickListener: (Ingredient) -> Unit
) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientRecordBinding.inflate(inflater)
        return IngredientViewHolder(binding) { longClickListener(ingredients[it]) }
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) =
        holder.bind(ingredients[position])

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(private val binding: ItemIngredientRecordBinding, longClickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnLongClickListener { longClickAtPosition(adapterPosition); true }
        }

        fun bind(ingredient: Ingredient) {
            binding.item = ingredient
        }
    }
}