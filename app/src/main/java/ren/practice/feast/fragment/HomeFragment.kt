package ren.practice.feast.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ren.practice.feast.R
import ren.practice.feast.adapter.MealAdapter
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
        binding.recyclerMeals.adapter = MealAdapter(DataSource.readMeals())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_new_recipe -> {
            val action = HomeFragmentDirections.actionHomeFragmentToNewRecipe()
            binding.root.findNavController().navigate(action)
            true
        }
        R.id.item_recipe_list -> {
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
            binding.root.findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}