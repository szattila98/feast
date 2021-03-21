package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.Recipe

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeName: TextView = view.findViewById(R.id.text_recipe_name)
        val created: TextView = view.findViewById(R.id.text_recipe_created)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.item_recipe_record, parent, false)
        return RecipeViewHolder(view)
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