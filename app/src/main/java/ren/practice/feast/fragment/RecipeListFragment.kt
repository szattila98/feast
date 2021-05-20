package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentRecipeListBinding
import ren.practice.feast.viewModel.RecipeListViewModel

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerRecipes.adapter = viewModel.recipes.value?.let { meals ->
            RecipeAdapter(meals) {
                val action = RecipeListFragmentDirections
                    .actionRecipeListFragmentToRecipeDetailsFragment(it.id)
                binding.root.findNavController().navigate(action)
            }
        }
        binding.recyclerRecipes.adapter?.notifyDataSetChanged()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}