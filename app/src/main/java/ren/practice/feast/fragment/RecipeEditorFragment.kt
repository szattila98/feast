package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeEditorBinding
import ren.practice.feast.viewModel.RecipeEditorViewModel

class RecipeEditorFragment : Fragment() {

    private var _binding: FragmentRecipeEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeEditorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEditorBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            val recipeId = RecipeEditorFragmentArgs.fromBundle(it).recipeId
            if (recipeId != 0L) {
                viewModel.recipeId = recipeId
                viewModel.setRecipeDetailsToEdit()
            }
        }

        viewModel.ingredients.observe(viewLifecycleOwner) {
            binding.recyclerIngredients.adapter = IngredientAdapter(it) { ingredient ->
                viewModel.removeIngredient(ingredient)
                binding.recyclerIngredients.adapter?.notifyDataSetChanged()
            }
        }
        viewModel.descriptionList.observe(viewLifecycleOwner) {
            binding.recyclerDescriptions.adapter =
                DescriptionAdapter(it) { description ->
                    viewModel.removeDescriptionRecord(description)
                    binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
                }
        }

        binding.buttonAddIngredient.setOnClickListener { addIngredient(); }
        binding.buttonAddDescription.setOnClickListener { addDescription(); }
        binding.fabConfirm.setOnClickListener { submitRecipe() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addIngredient() {
        viewModel.addIngredient()
        binding.recyclerIngredients.adapter?.notifyDataSetChanged()
    }

    private fun addDescription() {
        viewModel.addDescriptionRecord()
        binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
    }

    private fun submitRecipe() {
        val recipeId = runBlocking {
            viewModel.submitRecipe()
        }
        val action = RecipeEditorFragmentDirections.actionNewRecipeToRecipeDetailsFragment(recipeId)
        binding.root.findNavController().navigate(action)
    }
}