package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Meal
import ren.practice.core.domain.Recipe
import ren.practice.feast.data.DataSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MealEditorViewModel : ViewModel() {

    private var _recipes = MutableLiveData(listOf<Recipe>())
    val recipes: MutableLiveData<List<Recipe>> get() = _recipes

    private var _chosenRecipe = MutableLiveData<Recipe?>()
    val chosenRecipe: MutableLiveData<Recipe?> get() = _chosenRecipe

    private var _date = MutableLiveData<LocalDate>()
    val date: MutableLiveData<LocalDate> get() = _date

    private var _time = MutableLiveData<LocalTime>()
    val time: MutableLiveData<LocalTime> get() = _time

    private var _newMealName = MutableLiveData("")
    val newMealName: MutableLiveData<String> get() = _newMealName

    private var _chosenRecipeId = MutableLiveData<Long?>(null)

    init {
        _recipes.value = DataSource.getRecipes()
    }

    fun setDate(date: LocalDate) {
        _date.value = date
    }

    fun setTime(time: LocalTime) {
        _time.value = time
    }

    fun setChosenRecipeId(id: Long) {
        if (_chosenRecipeId.value != id) {
            _chosenRecipeId.value = id
        } else {
            _chosenRecipeId.value = null
        }
        _chosenRecipe.value = null
        _chosenRecipeId.value?.let { _chosenRecipe.value = DataSource.readRecipe(it) }
    }

    fun saveMeal(): Boolean {
        val dateTime = LocalDateTime.of(_date.value, _time.value)
        val meal = Meal(name = _newMealName.value!!, recipeId = _chosenRecipeId.value, date = dateTime)
        DataSource.saveMeal(meal)
        return true
    }
}