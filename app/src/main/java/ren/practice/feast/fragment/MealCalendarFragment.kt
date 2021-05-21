package ren.practice.feast.fragment

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
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentMealCalendarBinding
import ren.practice.feast.viewModel.MealCalendarViewModel
import java.time.LocalDate


class MealCalendarFragment : Fragment() {

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
        addCalendarDots()
        initRecycler()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.item_new_recipe -> {
            val action = MealCalendarFragmentDirections.actionHomeFragmentToNewRecipe()
            binding.root.findNavController().navigate(action)
            true
        }
        R.id.item_recipe_list -> {
            val action = MealCalendarFragmentDirections.actionHomeFragmentToRecipeListFragment()
            binding.root.findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initCalendar() {
        val currentDate = LocalDate.now()
        val plusDaysMin = 7 + currentDate.dayOfWeek.value - 1
        val plusDaysMax = 7 + (7 - currentDate.dayOfWeek.value)
        binding.calendarWeek.state().edit()
            .setMinimumDate(getCalendarDay(currentDate.plusDays((-plusDaysMin).toLong())))
            .setMaximumDate(getCalendarDay(currentDate.plusDays((plusDaysMax).toLong())))
            .commit()
        binding.calendarWeek.selectedDate = CalendarDay.today()
        binding.calendarWeek.setOnDateChangedListener { _, day, _ ->
            viewModel.readRelevantMeals(LocalDate.of(day.year, day.month, day.day))
            binding.recyclerMeals.adapter?.notifyDataSetChanged()
        }
    }

    private fun addCalendarDots() {
        val meals = DataSource.readMeals()
        val dates: MutableSet<CalendarDay> = mutableSetOf()
        for (meal in meals) {
            val date = meal.date.toLocalDate()
            dates.add(getCalendarDay(date))
        }
        binding.calendarWeek.addDecorator(EventDecorator(resources.getColor(R.color.red, context?.theme), dates))
    }

    private fun getCalendarDay(date: LocalDate): CalendarDay {
        return CalendarDay.from(date.year, date.monthValue, date.dayOfMonth)
    }

    private fun initRecycler() {
        viewModel.readRelevantMeals(LocalDate.now())
        binding.recyclerMeals.adapter = viewModel.meals.value?.let { meals ->
            MealAdapter(meals) { meal ->
                meal.recipeId?.let {
                    val action = MealCalendarFragmentDirections
                        .actionHomeFragmentToRecipeDetailsFragment(it)
                    binding.root.findNavController().navigate(action)
                }
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