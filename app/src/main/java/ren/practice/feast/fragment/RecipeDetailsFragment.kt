package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ren.practice.feast.adapter.DescriptionAdapter
import ren.practice.feast.adapter.IngredientAdapter
import ren.practice.feast.databinding.FragmentRecipeDetailsBinding

class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        arguments?.let {
            val recipe = RecipeDetailsFragmentArgs.fromBundle(it).recipe
            binding.textRecipeDetailsName.text = recipe.name
            binding.textRecipeDetailsCreated.text = recipe.created.toString()
            binding.recyclerRecipeDetailsIngredients.adapter =
                IngredientAdapter(recipe.ingredients)
            binding.recyclerRecipeDetailsDescriptions.adapter =
                DescriptionAdapter(recipe.description)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}