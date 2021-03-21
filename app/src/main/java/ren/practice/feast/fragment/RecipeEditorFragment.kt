package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentRecipeEditorBinding
import ren.practice.feast.model.DescriptionRecord
import ren.practice.feast.model.Ingredient
import ren.practice.feast.model.Recipe

class RecipeEditorFragment : Fragment() {

    private var _binding: FragmentRecipeEditorBinding? = null
    private val binding get() = _binding!!

    private val ingredients = mutableListOf<Ingredient>()
    private val descriptionList = mutableListOf<DescriptionRecord>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEditorBinding.inflate(inflater, container, false)
        binding.recyclerIngredients.adapter = IngredientAdapter(requireContext(), ingredients)
        binding.recyclerDescriptions.adapter = DescriptionAdapter(requireContext(), descriptionList)
        binding.buttonAddIngredient.setOnClickListener { addIngredient(); }
        binding.buttonAddDescription.setOnClickListener { addDescription(); }
        binding.fabConfirm.setOnClickListener { addRecipe() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addIngredient() {
        ingredients.add(
            Ingredient(
                binding.editIngredientName.text.toString(),
                binding.editIngredientAmount.text.toString(),
                binding.editIngredientUnit.text.toString()
            )
        )
        binding.recyclerIngredients.adapter?.notifyDataSetChanged()
        clearEditTexts(
            listOf(
                binding.editIngredientName,
                binding.editIngredientAmount,
                binding.editIngredientUnit
            )
        )
    }

    private fun addDescription() {
        val description = DescriptionRecord(binding.editDescriptionText.text.toString())
        descriptionList.add(description)
        binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
        clearEditTexts(listOf(binding.editDescriptionText))
    }

    private fun clearEditTexts(editTexts: List<EditText>) {
        for (v in editTexts) {
            v.text?.clear()
        }
    }

    private fun addRecipe() {
        val recipe = Recipe(
            1,
            binding.editRecipeName.text.toString(),
            ingredients,
            descriptionList
        )
        DataSource.saveRecipe(recipe)
        val action = RecipeEditorFragmentDirections.actionNewRecipeToRecipeListFragment()
        binding.root.findNavController().navigate(action)
    }
}