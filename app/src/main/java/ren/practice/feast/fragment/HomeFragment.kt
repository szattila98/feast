package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ren.practice.feast.adapter.DayPlanAdapter
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val dayPlanRecyclerView = binding.dayPlanRecyclerview
        val linearLayoutManager = LinearLayoutManager(context)
        val myDataSet = DataSource().readDayPlan()
        val dayPlanAdapter = DayPlanAdapter(myDataSet)
        dayPlanRecyclerView.apply {
            adapter = dayPlanAdapter
            layoutManager = linearLayoutManager
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}