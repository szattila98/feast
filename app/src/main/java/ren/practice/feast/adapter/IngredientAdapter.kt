package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.Ingredient

class IngredientAdapter(
    private val context: Context,
    private val dataSet: MutableList<Ingredient>
) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.text_ingredient_name)
        val ingredientAmount: TextView = view.findViewById(R.id.text_ingredient_amount)
        val ingredientUnit: TextView = view.findViewById(R.id.text_ingredient_unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_ingredient_record, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = dataSet[position]
        holder.apply {
            ingredientName.text = ingredient.name
            ingredientAmount.text = ingredient.amount
            ingredientUnit.text = ingredient.unit
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}