package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ren.practice.feast.databinding.ItemDescriptionRecordBinding
import ren.practice.feast.model.DescriptionRecord

class DescriptionAdapter(
    private val descriptionRecords: MutableList<DescriptionRecord>
) : RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDescriptionRecordBinding.inflate(inflater)
        return DescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) =
        holder.bind(descriptionRecords[position], position)

    override fun getItemCount(): Int = descriptionRecords.size

    class DescriptionViewHolder(private val binding: ItemDescriptionRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(descriptionRecord: DescriptionRecord, position: Int) {
            binding.item = descriptionRecord
            binding.num = position + 1
            binding.executePendingBindings()
        }
    }
}