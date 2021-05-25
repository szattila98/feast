package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Meal
import ren.practice.framework.Interactors
import java.time.LocalDate

class HomeViewModel(private val interactors: Interactors) : ViewModel() {

    val relevantMeals = MutableLiveData(listOf<Meal>())
    val everyMeal = MutableLiveData(listOf<Meal>())
    val currentDate = MutableLiveData(LocalDate.now())

    fun readEveryMeal() = GlobalScope.launch {
        everyMeal.postValue(interactors.findAllMeals())
    }

    fun readRelevantMeals() = GlobalScope.launch {
        getRelevantMeals()
    }

    fun deleteMeal(id: Long) = GlobalScope.launch {
        interactors.deleteMeal(id)
        getRelevantMeals()
    }

    private suspend fun getRelevantMeals() {
        currentDate.value?.let {
            relevantMeals.postValue(interactors.findRelevantMeals(it))
        }
    }
}