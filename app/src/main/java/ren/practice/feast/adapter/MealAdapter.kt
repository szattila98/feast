package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.databinding.ItemMealRecordBinding
import ren.practice.feast.fragment.HomeFragmentDirections
import ren.practice.feast.model.Meal

// TODO to mealList
class MealAdapter(private val meals: List<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealRecordBinding.inflate(inflater)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
        // TODO callback
        holder.binding.textMealRecipeLabel.setOnClickListener {
            val action = meal.recipe?.let { it1 ->
                HomeFragmentDirections
                    .actionHomeFragmentToRecipeDetailsFragment(it1)
            }
            if (action != null) {
                Navigation.findNavController(holder.itemView).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = meals.size

    class MealViewHolder(val binding: ItemMealRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.item = meal
            binding.executePendingBindings()
        }
    }
}
