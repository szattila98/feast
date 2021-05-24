package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Meal
import ren.practice.framework.Interactors
import java.time.LocalDate

class MealCalendarViewModel(private val interactors: Interactors) : ViewModel() {

    private var _relevantMeals = MutableLiveData(mutableListOf<Meal>())
    val relevantMeals get() = _relevantMeals

    private var _currentDate = MutableLiveData(LocalDate.now())
    val currentDate: LiveData<LocalDate> get() = _currentDate

    suspend fun readEveryMeal() = interactors.findAllMeals()

    fun setCurrentDate(date: LocalDate) {
        _currentDate.value = date
    }

    fun readRelevantMeals() {
        GlobalScope.launch {
            _currentDate.value?.let {
                loadMeals(interactors.findRelevantMeals(it))
            }
        }
    }

    private fun loadMeals(meals: List<Meal>) {
        _relevantMeals.value?.clear()
        _relevantMeals.value?.addAll(meals)
    }

    fun deleteMeal(id: Long) {
        GlobalScope.launch {
            interactors.deleteMeal(id)
            readRelevantMeals()
        }
    }
}