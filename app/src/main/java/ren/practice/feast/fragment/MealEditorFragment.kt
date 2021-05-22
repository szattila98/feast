package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentMealEditorBinding
import ren.practice.feast.viewModel.MealEditorViewModel
import java.time.LocalTime

class MealEditorFragment : Fragment() {

    private var _binding: FragmentMealEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealEditorBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        arguments?.let {
            MealEditorFragmentArgs.fromBundle(it).selectedDate?.let { selectedDate ->
                viewModel.setDate(selectedDate)
            }
            val mealId = MealEditorFragmentArgs.fromBundle(it).mealId
            if (mealId != 0L) {
                viewModel.mealId = MealEditorFragmentArgs.fromBundle(it).mealId
                viewModel.setRecipeDetailsToEdit()
            }
        }
        binding.recyclerRecipes.adapter =
            viewModel.recipes.value?.let { recipes ->
                RecipeAdapter(recipes) {
                    viewModel.setChosenRecipeId(it.id)
                }
            }
        binding.timepickerMeal.setIs24HourView(true)
        binding.timepickerMeal.setOnTimeChangedListener { _, hour, min ->
            viewModel.setTime(LocalTime.of(hour, min))
        }
        binding.buttonSaveMeal.setOnClickListener {
            viewModel.saveMeal()
            val action = MealEditorFragmentDirections.actionMealEditorFragmentToHomeFragment()
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}