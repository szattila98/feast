package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
                viewModel.recipeId.value = recipeId
                viewModel.setRecipeDetailsToEdit()
            }
        }
    }

    private fun initListObservers() {
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
    }

    private fun initButtons() {
        binding.buttonAddIngredient.setOnClickListener {
            viewModel.addIngredient()
            binding.recyclerIngredients.adapter?.notifyDataSetChanged()
        }
        binding.buttonAddDescription.setOnClickListener {
            viewModel.addDescriptionRecord()
            binding.recyclerDescriptions.adapter?.notifyDataSetChanged()
        }
        binding.fabConfirm.setOnClickListener { submitRecipe() }
    }

    private fun submitRecipe() {
        viewModel.newRecipeId.observe(viewLifecycleOwner) {
            val action = RecipeEditorFragmentDirections.actionNewRecipeToRecipeDetailsFragment(it)
            binding.root.findNavController().navigate(action)
        }
        viewModel.submitRecipe()
    }
}