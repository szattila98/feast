package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ren.practice.feast.R
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeEditorBinding
import ren.practice.feast.viewModel.RecipeEditorViewModel

class RecipeEditorFragment : Fragment() {

    private var _binding: FragmentRecipeEditorBinding? = null
    private val binding get() = _binding!!

    private val recipeEditorViewModel: RecipeEditorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeEditorBinding.inflate(inflater, container, false)
        binding.viewModel = recipeEditorViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initArguments()
        initListObservers()
        initButtons()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initArguments() {
        arguments?.let {
            val recipeId = RecipeEditorFragmentArgs.fromBundle(it).recipeId
            if (recipeId != 0L) {
                recipeEditorViewModel.recipeId.value = recipeId
                recipeEditorViewModel.setRecipeDetailsToEdit()
            }
        }
    }

    private fun initListObservers() {
        recipeEditorViewModel.ingredients.observe(viewLifecycleOwner) {
            binding.recyclerIngredients.adapter = IngredientAdapter(it) { ingredient ->
                recipeEditorViewModel.removeIngredient(ingredient)
                binding.recyclerIngredients.adapter?.notifyDataSetChanged()
            }
        }
        recipeEditorViewModel.descriptionList.observe(viewLifecycleOwner) {
            binding.recyclerDescriptions.adapter =
                DescriptionAdapter(it) { description ->
                    recipeEditorViewModel.removeDescriptionRecord(description)
                    binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
                }
        }
    }

    private fun initButtons() {
        binding.buttonAddIngredient.setOnClickListener {
            recipeEditorViewModel.addIngredient()
            binding.recyclerIngredients.adapter?.notifyDataSetChanged()
        }
        binding.buttonAddDescription.setOnClickListener {
            recipeEditorViewModel.addDescriptionRecord()
            binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
        }
        binding.fabConfirm.setOnClickListener { submitRecipe() }
    }

    private fun submitRecipe() {
        if (recipeEditorViewModel.recipeName.value!!.isNotEmpty()) {
            recipeEditorViewModel.newRecipeId.observe(viewLifecycleOwner) {
                val action = RecipeEditorFragmentDirections.actionNewRecipeToRecipeDetailsFragment(it)
                binding.root.findNavController().navigate(action)
            }
            recipeEditorViewModel.submitRecipe()
            return
        }
        Toast.makeText(requireContext(), getString(R.string.validation_msg_recipe_name), Toast.LENGTH_SHORT).show()
    }
}