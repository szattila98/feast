package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val clickListener: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.text_recipe_name)
        val created: TextView = view.findViewById(R.id.text_recipe_created)

        init {
            itemView.setOnClickListener { clickAtPosition(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.item_recipe_record, parent, false)
        return RecipeViewHolder(view) { clickListener(recipes[it]) }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.apply {
            recipeName.text = recipe.name
            created.text = recipe.created.toString()
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}