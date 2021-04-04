package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeEditorBinding
import ren.practice.feast.model.DescriptionRecord
import ren.practice.feast.model.Ingredient
import ren.practice.feast.model.Recipe
import ren.practice.feast.viewModel.RecipeEditorViewModel

class RecipeEditorFragment : Fragment() {

    private var _binding: FragmentRecipeEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEditorBinding.inflate(inflater, container, false)

        binding.recipeEditorViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerIngredients.adapter =
            viewModel.ingredients.value?.let { IngredientAdapter(it) }
        binding.recyclerDescriptions.adapter =
            viewModel.descriptionList.value?.let { DescriptionAdapter(it) }

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
        viewModel.addIngredient(
            Ingredient(
                binding.editIngredientName.text.toString(),
                binding.editIngredientAmount.text.toString(),
                binding.editIngredientUnit.text.toString()
            )
        )
        binding.recyclerIngredients.adapter?.notifyDataSetChanged()
        clearEditTexts(
            binding.editIngredientName,
            binding.editIngredientAmount,
            binding.editIngredientUnit
        )
    }

    private fun addDescription() {
        viewModel.addDescriptionRecord(
            DescriptionRecord(binding.editDescriptionText.text.toString())
        )
        binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
        clearEditTexts(binding.editDescriptionText)
    }

    private fun clearEditTexts(vararg editTexts: EditText) {
        for (v in editTexts) {
            v.text?.clear()
        }
    }

    private fun submitRecipe() {
        val recipe = Recipe(
            1,
            binding.editRecipeName.text.toString(),
            viewModel.ingredients.value!!,
            viewModel.descriptionList.value!!
        )
        viewModel.submitRecipe(recipe)
        val action = RecipeEditorFragmentDirections.actionNewRecipeToRecipeDetailsFragment(recipe)
        binding.root.findNavController().navigate(action)
    }
}