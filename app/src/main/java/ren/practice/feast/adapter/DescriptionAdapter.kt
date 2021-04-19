package ren.practice.feast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ren.practice.core.domain.Description
import ren.practice.feast.databinding.ItemDescriptionRecordBinding

class DescriptionAdapter(
    private val descriptions: MutableList<Description>
) : RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDescriptionRecordBinding.inflate(inflater)
        return DescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) =
        holder.bind(descriptions[position], position)

    override fun getItemCount(): Int = descriptions.size

    class DescriptionViewHolder(private val binding: ItemDescriptionRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(description: Description, position: Int) {
            binding.item = description
            binding.num = position + 1
            binding.executePendingBindings()
        }
    }
}