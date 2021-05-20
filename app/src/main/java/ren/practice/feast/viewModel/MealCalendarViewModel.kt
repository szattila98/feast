package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Meal
import ren.practice.feast.data.DataSource
import java.time.LocalDate

class MealCalendarViewModel : ViewModel() {

    private var _meals = MutableLiveData(mutableListOf<Meal>())
    val meals: LiveData<MutableList<Meal>>
        get() = _meals

    fun readRelevantMeals(date: LocalDate) {
        loadMeals(DataSource.readRelevantMeals(date))
    }

    private fun loadMeals(meals: List<Meal>) {
        _meals.value?.clear()
        _meals.value?.addAll(meals)
    }
}