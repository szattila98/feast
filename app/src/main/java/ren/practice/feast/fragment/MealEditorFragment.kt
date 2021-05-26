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
import ren.practice.feast.adapter.RecipeAdapter
import ren.practice.feast.databinding.FragmentMealEditorBinding
import ren.practice.feast.viewModel.MealEditorViewModel
import java.time.LocalTime

class MealEditorFragment : Fragment() {

    private var _binding: FragmentMealEditorBinding? = null
    private val binding get() = _binding!!

    private val mealEditorViewModel: MealEditorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealEditorBinding.inflate(inflater, container, false)
        binding.viewModel = mealEditorViewModel
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
                mealEditorViewModel.date.postValue(selectedDate)
            }
            val mealId = MealEditorFragmentArgs.fromBundle(it).mealId
            if (mealId != 0L) {
                mealEditorViewModel.mealId.value = mealId
                mealEditorViewModel.setMealDetailsToEdit()
            }
        }
    }

    private fun initRecipeListObserver() {
        mealEditorViewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            binding.recyclerRecipes.adapter = RecipeAdapter(recipes) { recipe ->
                mealEditorViewModel.chooseRecipe(recipe)
            }
        }
    }

    private fun initTimePicker() {
        binding.timepickerMeal.setIs24HourView(true)
        binding.timepickerMeal.hour = mealEditorViewModel.time.value!!.hour
        binding.timepickerMeal.minute = mealEditorViewModel.time.value!!.minute
        binding.timepickerMeal.setOnTimeChangedListener { _, hour, min ->
            mealEditorViewModel.time.postValue(LocalTime.of(hour, min))
        }
    }

    private fun initSaveFab() {
        binding.fabSaveMeal.setOnClickListener {
            if (mealEditorViewModel.mealName.value!!.isNotEmpty()) {
                mealEditorViewModel.saveMeal()
                val action = MealEditorFragmentDirections.actionMealEditorFragmentToHomeFragment()
                binding.root.findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), getString(R.string.validation_msg_meal_name), Toast.LENGTH_SHORT).show()
            }
        }
    }

}