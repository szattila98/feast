package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentMealEditorBinding
import ren.practice.feast.viewModel.MealEditorViewModel
import java.time.LocalTime

class MealEditorFragment : Fragment() {

    private var _binding: FragmentMealEditorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealEditorViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealEditorBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initArguments()
        initRecipeListObserver()
        initTimePicker()
        initSaveFab()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initArguments() {
        arguments?.let {
            MealEditorFragmentArgs.fromBundle(it).selectedDate?.let { selectedDate ->
                viewModel.date.postValue(selectedDate)
            }
            val mealId = MealEditorFragmentArgs.fromBundle(it).mealId
            if (mealId != 0L) {
                viewModel.mealId.value = mealId
                viewModel.setMealDetailsToEdit()
            }
        }
    }

    private fun initRecipeListObserver() {
        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerRecipes.adapter = RecipeAdapter(recipes) { recipe ->
                viewModel.chooseRecipe(recipe)
            }
        }
    }

    private fun initTimePicker() {
        binding.timepickerMeal.setIs24HourView(true)
        binding.timepickerMeal.hour = viewModel.time.value!!.hour
        binding.timepickerMeal.minute = viewModel.time.value!!.minute
        binding.timepickerMeal.setOnTimeChangedListener { _, hour, min ->
            viewModel.time.postValue(LocalTime.of(hour, min))
        }
    }

    private fun initSaveFab() {
        binding.fabSaveMeal.setOnClickListener {
            viewModel.saveMeal()
            val action = MealEditorFragmentDirections.actionMealEditorFragmentToHomeFragment()
            binding.root.findNavController().navigate(action)
        }
    }

}