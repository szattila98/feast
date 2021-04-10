package ren.practice.feast.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import ren.practice.feast.R
import ren.practice.feast.data.DataSource
import ren.practice.feast.databinding.FragmentHomeBinding
import java.time.LocalDate


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initCalendar()
        addCalendarDots()

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
            val action = HomeFragmentDirections.actionHomeFragmentToNewRecipe()
            binding.root.findNavController().navigate(action)
            true
        }
        R.id.item_recipe_list -> {
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeListFragment()
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
            .setMinimumDate(
                CalendarDay.from(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth - plusDaysMin)
            )
            .setMaximumDate(
                CalendarDay.from(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth + plusDaysMax)
            )
            .commit()
        binding.calendarWeek.selectedDate = CalendarDay.today()
    }

    private fun addCalendarDots() {
        val meals = DataSource.readMeals()
        val dates: MutableSet<CalendarDay> = mutableSetOf()
        for (meal in meals) {
            val date = meal.date.toLocalDate()
            dates.add(CalendarDay.from(date.year, date.monthValue, date.dayOfMonth))
        }
        binding.calendarWeek.addDecorator(EventDecorator(resources.getColor(R.color.red, context?.theme), dates))
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