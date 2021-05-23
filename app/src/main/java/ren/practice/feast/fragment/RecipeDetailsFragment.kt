package ren.practice.feast.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
                IngredientAdapter(it.ingredients) {}
            binding.recyclerRecipeDetailsDescriptions.adapter =
                DescriptionAdapter(it.description) {}
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_edit_recipe -> {
            viewModel.recipe.value?.let {
                val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToNewRecipeFragment(it.id)
                findNavController().navigate(action)
            }
            true
        }
        R.id.menu_item_delete_recipe -> {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.recipe_details_delete_dialog_title)
                .setPositiveButton(R.string.recipe_details_dialog_delete) { dialog, _ ->
                    dialog.dismiss()
                    val success = viewModel.deleteRecipe()
                    if (success) {
                        val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeListFragment()
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(requireContext(), R.string.recipe_details_delete_dialog_toast, Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(R.string.recipe_details_dialog_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}