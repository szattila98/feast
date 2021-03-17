package ren.practice.feast.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ren.practice.feast.R
import ren.practice.feast.adapter.DayPlanAdapter
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val dayPlanRecyclerView = binding.recyclerDayPlans
        val linearLayoutManager = LinearLayoutManager(context)
        val myDataSet = DataSource().readDayPlan()
        val dayPlanAdapter = DayPlanAdapter(requireContext(), myDataSet)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_recipe_menu_item -> {
                val action = HomeFragmentDirections.actionHomeFragmentToNewRecipe()
                binding.root.findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}