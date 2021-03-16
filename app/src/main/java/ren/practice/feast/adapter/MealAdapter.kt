package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.Meal

class MealAdapter(private val context: Context, private val meals: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val mealShownName: TextView = view.findViewById(R.id.meal_card_shown_name)
        val recipeLabel: TextView = view.findViewById(R.id.meal_card_recipe_label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.meal_card, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealShownName.text =
            context.getString(R.string.meal_name, meal.orderNum, meal.shownName)
        holder.recipeLabel.text = meal.recipe?.name
    }

    override fun getItemCount(): Int {
        return meals.size
    }
}