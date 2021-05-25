package ren.practice.feast.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.koin.android.ext.android.inject
import ren.practice.feast.R
import ren.practice.feast.adapter.MealAdapter
import ren.practice.feast.databinding.FragmentMealCalendarBinding
import ren.practice.feast.viewModel.HomeViewModel
import java.time.LocalDate


class HomeFragment : Fragment() {

    private var _binding: FragmentMealCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by inject()

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
        viewModel.readEveryMeal()
        initCalendarDotObserver()
        initRecycler()
        initFab()

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
                viewModel.currentDate.value = LocalDate.of(day.year, day.month, day.day)
                viewModel.readRelevantMeals()
            }
        }
    }

    private fun initCalendarDotObserver() {
        viewModel.everyMeal.observe(viewLifecycleOwner) {
            val dates: MutableSet<CalendarDay> = mutableSetOf()
            for (meal in it) {
                val date = meal.date.toLocalDate()
                dates.add(getCalendarDay(date))
            }
            binding.calendarWeek.removeDecorators()
            binding.calendarWeek.addDecorator(EventDecorator(resources.getColor(R.color.red, context?.theme), dates))
        }
    }

    private fun getCalendarDay(date: LocalDate): CalendarDay {
        return CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
    }

    private fun initRecycler() {
        viewModel.readRelevantMeals()
        val adapter = viewModel.relevantMeals.value?.let { meals ->
            MealAdapter(
                meals.toMutableList(),
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
        binding.recyclerMeals.adapter = adapter
        viewModel.relevantMeals.observe(viewLifecycleOwner) {
            adapter?.update(it)
        }
    }

    private fun showMenuDialog(mealId: Long) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.meal_dialog_title)
            .setPositiveButton(R.string.meal_dialog_edit_btn) { dialog, _ ->
                dialog.dismiss()
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMealEditorFragment(mealId = mealId)
                binding.root.findNavController().navigate(action)
                viewModel.readRelevantMeals()
                binding.recyclerMeals.adapter?.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.meal_dialog_delete_btn) { dialog, _ ->
                dialog.dismiss()
                viewModel.deleteMeal(mealId)
                binding.recyclerMeals.adapter?.notifyDataSetChanged()
                viewModel.readEveryMeal()
            }
            .setNeutralButton(R.string.meal_dialog_cancel_btn) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun initFab() {
        binding.fabNewMeal.setOnClickListener {
            viewModel.currentDate.value?.let {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToMealEditorFragment(it)
                binding.root.findNavController().navigate(action)
            }
        }
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