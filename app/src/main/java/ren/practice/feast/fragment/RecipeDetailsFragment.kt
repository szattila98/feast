package ren.practice.feast.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ren.practice.feast.R
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeDetailsBinding
import ren.practice.feast.viewModel.RecipeDetailsViewModel

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipe_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}