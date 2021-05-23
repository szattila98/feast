package ren.practice.feast.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import ren.practice.feast.R
import ren.practice.feast.adapter.MealAdapter
import ren.practice.feast.databinding.FragmentMealCalendarBinding
import ren.practice.feast.viewModel.MealCalendarViewModel
import java.time.LocalDate


class HomeFragment : Fragment() {

    private var _binding: FragmentMealCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealCalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealCalendarBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initCalendar()
        updateCalendarDots()
        initRecycler()
        binding.fabNewMeal.setOnClickListener {
            val selectedDate = binding.calendarWeek.selectedDate!!
            val action = HomeFragmentDirections
                .actionHomeFragmentToMealEditorFragment(
                    LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.day)
                )
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_meal_calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_item_new_recipe -> {
            val action = HomeFragmentDirections.actionHomeFragmentToNewRecipe()
            binding.root.findNavController().navigate(action)
            true
        }
        R.id.menu_item_recipe_list -> {
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
            binding.root.findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initCalendar() {
        viewModel.currentDate.value?.let { currentDate ->
            val plusDaysMin = 7 + currentDate.dayOfWeek.value - 1
            val plusDaysMax = 7 + (7 - currentDate.dayOfWeek.value)
            binding.calendarWeek.state().edit()
                .setMinimumDate(getCalendarDay(currentDate.plusDays((-plusDaysMin).toLong())))
                .setMaximumDate(getCalendarDay(currentDate.plusDays((plusDaysMax).toLong())))
                .commit()
            binding.calendarWeek.selectedDate = CalendarDay.from(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
            binding.calendarWeek.setOnDateChangedListener { _, day, _ ->
                viewModel.setCurrentDate(LocalDate.of(day.year, day.month, day.day))
                viewModel.readRelevantMeals()
                binding.recyclerMeals.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun updateCalendarDots() {
        val meals = viewModel.readEveryMeal()
        val dates: MutableSet<CalendarDay> = mutableSetOf()
        for (meal in meals) {
            val date = meal.date.toLocalDate()
            dates.add(getCalendarDay(date))
        }
        binding.calendarWeek.removeDecorators()
        binding.calendarWeek.addDecorator(EventDecorator(resources.getColor(R.color.red, context?.theme), dates))
    }

    private fun getCalendarDay(date: LocalDate): CalendarDay {
        return CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
    }

    private fun initRecycler() {
        viewModel.readRelevantMeals()
        binding.recyclerMeals.adapter = viewModel.relevantMeals.value?.let { meals ->
            MealAdapter(
                meals,
                clickListener = { meal ->
                    meal.recipeId?.let {
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToRecipeDetailsFragment(it)
                        binding.root.findNavController().navigate(action)
                    }
                },
                longClickListener = {
                    showMenuDialog(it.id)
                }
            )
        }
    }

    private fun showMenuDialog(mealId: Long) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.meal_menu_title)
            .setPositiveButton(R.string.meal_menu_edit) { dialog, _ ->
                dialog.dismiss()
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMealEditorFragment(mealId = mealId)
                binding.root.findNavController().navigate(action)
                viewModel.readRelevantMeals()
                binding.recyclerMeals.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.meal_menu_delete) { dialog, _ ->
                dialog.dismiss()
                viewModel.deleteMeal(mealId)
                binding.recyclerMeals.adapter?.notifyDataSetChanged()
                updateCalendarDots()
            }
            .setNeutralButton(R.string.meal_menu_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    class EventDecorator(private val color: Int, private val dates: Set<CalendarDay>) : DayViewDecorator {

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(DotSpan(12F, color))
        }
    }
}