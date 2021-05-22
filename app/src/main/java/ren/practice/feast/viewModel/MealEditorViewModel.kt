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

    private var _mealName = MutableLiveData("")
    val mealName: MutableLiveData<String> get() = _mealName

    private var _chosenRecipeId = MutableLiveData<Long?>(null)

    private var _mealId = MutableLiveData(0L)
    var mealId: Long?
        get() = _mealId.value
        set(value) {
            _mealId.value = value
        }

    init {
        _recipes.value = DataSource.getRecipes()
    }

    fun setMealName(name: String) {
        _mealName.value = name
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

    fun saveMeal(): Boolean { // TODO validation
        val dateTime = LocalDateTime.of(_date.value, _time.value)
        val meal = Meal(mealId!!, dateTime, _mealName.value!!, _chosenRecipeId.value)
        DataSource.saveMeal(meal)
        return true
    }

    fun setRecipeDetailsToEdit() {
        val meal = DataSource.readMeal(mealId!!)
        setMealName(meal.name)
        setDate(meal.date.toLocalDate())
        setTime(meal.date.toLocalTime())
        meal.recipeId?.let {
            setChosenRecipeId(it)
        }
    }

}