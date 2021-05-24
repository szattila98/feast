package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentRecipeListBinding
import ren.practice.feast.viewModel.RecipeListViewModel

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerRecipes.adapter = RecipeAdapter(recipes) { recipe ->
                val action = RecipeListFragmentDirections
                    .actionRecipeListFragmentToRecipeDetailsFragment(recipe.id)
                binding.root.findNavController().navigate(action)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}