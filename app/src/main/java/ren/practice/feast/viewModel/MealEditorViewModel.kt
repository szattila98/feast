package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Meal
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MealEditorViewModel(private val interactors: Interactors) : ViewModel() {

    val recipes = MutableLiveData<List<Recipe>>().apply {
        GlobalScope.launch {
            postValue(interactors.findAllRecipes())
        }
    }
    val mealId = MutableLiveData(0L)
    val mealName = MutableLiveData("")
    val date = MutableLiveData(LocalDate.now())
    val time = MutableLiveData(LocalTime.of(12, 0))
    private val chosenRecipeId = MutableLiveData<Long?>(null)
    val chosenRecipeName = MutableLiveData("")

    fun chooseRecipe(recipe: Recipe) {
        if (chosenRecipeId.value != recipe.id) {
            chosenRecipeId.postValue(recipe.id)
            chosenRecipeName.postValue(recipe.name)
        } else {
            chosenRecipeId.postValue(null)
            chosenRecipeName.postValue("")
        }
    }

    fun saveMeal() = GlobalScope.launch {
        val dateTime = LocalDateTime.of(date.value, time.value)
        val meal = Meal(mealId.value!!, dateTime, mealName.value!!, chosenRecipeId.value)
        interactors.saveMeal(meal)
    }

    fun setMealDetailsToEdit() = GlobalScope.launch {
        val meal = interactors.findMeal(mealId.value!!)
        mealName.postValue(meal.name)
        date.postValue(meal.date.toLocalDate())
        time.postValue(meal.date.toLocalTime())
        meal.recipeId?.let {
            chooseRecipe(interactors.findRecipe(it))
        }
    }
}