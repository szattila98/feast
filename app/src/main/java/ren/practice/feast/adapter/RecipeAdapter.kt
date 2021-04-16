package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.databinding.ItemRecipeRecordBinding
import ren.practice.feast.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val clickListener: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeRecordBinding.inflate(inflater)
        return RecipeViewHolder(binding) { clickListener(recipes[it]) }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipes[position])

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(
        private val binding: ItemRecipeRecordBinding,
        clickAtPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener { clickAtPosition(adapterPosition) }
        }

        fun bind(recipe: Recipe) {
            binding.item = recipe
            // binding.executePendingBindings()
        }
    }
}