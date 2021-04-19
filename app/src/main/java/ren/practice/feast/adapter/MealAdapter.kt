package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ren.practice.core.domain.Meal
import ren.practice.feast.databinding.ItemMealRecordBinding

class MealAdapter(
    private val meals: List<Meal>,
    private val clickListener: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealRecordBinding.inflate(inflater)
        return MealViewHolder(binding) { clickListener(meals[it]) }
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) = holder.bind(meals[position])

    override fun getItemCount(): Int {
        return meals.size
    }

    class MealViewHolder(
        private val binding: ItemMealRecordBinding,
        clickAtPosition: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonMealRecipe.setOnClickListener { clickAtPosition(adapterPosition) }
        }

        fun bind(meal: Meal) {
            binding.item = meal
            binding.time = meal.date.toLocalTime().toString()
            if (meal.recipe == null) binding.buttonMealRecipe.visibility = View.GONE
        }
    }
}