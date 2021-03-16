package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.DayPlan

class DayPlanAdapter(
    private val context: Context,
    private val dataSet: List<DayPlan>
) : RecyclerView.Adapter<DayPlanAdapter.DayPlanViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    class DayPlanViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val dayPlanDateTextView: TextView = view.findViewById(R.id.day_plan_date)
        val mealsRecyclerView: RecyclerView = view.findViewById(R.id.meal_recyclerview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlanViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.day_plan, parent, false)
        return DayPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayPlanViewHolder, position: Int) {
        val dayPlan = dataSet[position]
        val adapter = MealAdapter(context, dayPlan.meals)
        val layoutManager = LinearLayoutManager(
            holder.mealsRecyclerView.context, LinearLayoutManager.VERTICAL, false
        )
        layoutManager.initialPrefetchItemCount = dayPlan.meals.size

        holder.apply {
            dayPlanDateTextView.text = dayPlan.date.toString()

            mealsRecyclerView.layoutManager = layoutManager
            mealsRecyclerView.adapter = adapter
            mealsRecyclerView.setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}