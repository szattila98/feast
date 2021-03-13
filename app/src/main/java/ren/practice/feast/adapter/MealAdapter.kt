package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.Meal

class MealAdapter(private val meals: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealShownName: TextView = itemView.findViewById(R.id.meal_card_shown_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.meal_card, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealShownName.text = meal.shownName
    }

    override fun getItemCount(): Int {
        return meals.size
    }
}