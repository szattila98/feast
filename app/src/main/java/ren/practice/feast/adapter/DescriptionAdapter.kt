package ren.practice.feast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.R
import ren.practice.feast.model.DescriptionRecord

class DescriptionAdapter(
    private val context: Context,
    private val dataSet: MutableList<DescriptionRecord>
) : RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>() {

    class DescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val num: TextView = view.findViewById(R.id.text_description_num)
        val description: TextView = view.findViewById(R.id.text_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_description_record, parent, false)
        return DescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        val descriptionRecord = dataSet[position]
        holder.apply {
            num.text = (position + 1).toString()
            description.text = descriptionRecord.text
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}