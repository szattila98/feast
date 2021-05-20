package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeDetailsBinding
import ren.practice.feast.viewModel.RecipeDetailsViewModel

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            val recipeId = RecipeDetailsFragmentArgs.fromBundle(it).recipeId
            viewModel.loadRecipe(recipeId)
        }
        viewModel.recipe.value?.let {
            binding.textRecipeDetailsName.text = it.name
            binding.textRecipeDetailsCreated.text = it.created.toString()
            binding.recyclerRecipeDetailsIngredients.adapter =
                IngredientAdapter(it.ingredients)
            binding.recyclerRecipeDetailsDescriptions.adapter =
                DescriptionAdapter(it.description)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}