package ren.practice.feast.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import ren.practice.feast.R
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeDetailsBinding
import ren.practice.feast.viewModel.RecipeDetailsViewModel

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailsViewModel by inject()

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

        initArguments()

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
                .setTitle(R.string.delete_dialog_title)
                .setPositiveButton(R.string.delete_dialog_delete_option) { dialog, _ ->
                    dialog.dismiss()
                    viewModel.deleteRecipe()
                    viewModel.deleteSuccessful.observe(viewLifecycleOwner) {
                        if (it) {
                            val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeListFragment()
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(requireContext(), R.string.delete_dialog_toast, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton(R.string.delete_dialog_cancel_option) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initArguments() {
        arguments?.let {
            val recipeId = RecipeDetailsFragmentArgs.fromBundle(it).recipeId
            viewModel.loadRecipe(recipeId)
        }
        viewModel.recipe.observe(viewLifecycleOwner) {
            binding.textRecipeDetailsName.text = it.name
            binding.textRecipeDetailsCreated.text = it.created.toString()
            binding.recyclerRecipeDetailsIngredients.adapter =
                IngredientAdapter(it.ingredients) {}
            binding.recyclerRecipeDetailsDescriptions.adapter =
                DescriptionAdapter(it.description) {}
        }
    }
}