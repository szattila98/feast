package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.DayPlan

class DayPlanAdapter(
    private val context: Context,
    private val dataSet: List<DayPlan>
) : RecyclerView.Adapter<DayPlanAdapter.DayPlanViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    class DayPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayPlanDateTextView: TextView = view.findViewById(R.id.text_day_plan_date)
        val mealsRecyclerView: RecyclerView = view.findViewById(R.id.recycler_meals)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlanViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.day_plan, parent, false)
        return DayPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayPlanViewHolder, position: Int) {
        val dayPlan = dataSet[position]
        holder.apply {
            dayPlanDateTextView.text = dayPlan.date.toString()
            mealsRecyclerView.adapter = MealAdapter(context, dayPlan.meals)
            mealsRecyclerView.setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}