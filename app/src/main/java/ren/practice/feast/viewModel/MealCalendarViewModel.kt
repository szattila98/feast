package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Meal
import ren.practice.feast.data.DataSource
import java.time.LocalDate

class MealCalendarViewModel : ViewModel() {

    private var _relevantMeals = MutableLiveData(mutableListOf<Meal>())
    val relevantMeals get() = _relevantMeals

    private var _currentDate = MutableLiveData(LocalDate.now())
    val currentDate: LiveData<LocalDate> get() = _currentDate

    fun readEveryMeal() = DataSource.readMeals()

    fun setCurrentDate(date: LocalDate) {
        _currentDate.value = date
    }

    fun readRelevantMeals() {
        _currentDate.value?.let {
            loadMeals(DataSource.readRelevantMeals(it))
        }
    }

    private fun loadMeals(meals: List<Meal>) {
        _relevantMeals.value?.clear()
        _relevantMeals.value?.addAll(meals)
    }

    fun deleteMeal(id: Long) {
        DataSource.deleteMeal(id)
        readRelevantMeals()
    }
}