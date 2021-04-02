package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        binding.recyclerRecipes.adapter = RecipeAdapter(DataSource.getRecipes()) {
            val action = RecipeListFragmentDirections
                .actionRecipeListFragmentToRecipeDetailsFragment(it)
            binding.root.findNavController().navigate(action)
        }
        binding.recyclerRecipes.adapter?.notifyDataSetChanged()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}