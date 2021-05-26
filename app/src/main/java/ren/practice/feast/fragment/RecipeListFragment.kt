package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentRecipeListBinding
import ren.practice.feast.viewModel.RecipeListViewModel

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val recipeListViewModel: RecipeListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        binding.viewModel = recipeListViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecipeListObserver()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecipeListObserver() {
        recipeListViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerRecipes.adapter = RecipeAdapter(recipes) { recipe ->
                val action = RecipeListFragmentDirections
                    .actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
                binding.root.findNavController().navigate(action)
            }
        }
    }
}