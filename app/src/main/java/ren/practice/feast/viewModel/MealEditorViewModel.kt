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

    private var _recipes = MutableLiveData<List<Recipe>>().apply {
        GlobalScope.launch {
            postValue(interactors.findAllRecipes())
        }
    }
    val recipes: MutableLiveData<List<Recipe>> get() = _recipes

    private var _chosenRecipeName = MutableLiveData("")
    val chosenRecipeName: MutableLiveData<String> get() = _chosenRecipeName

    private var _date = MutableLiveData<LocalDate>()
    val date: MutableLiveData<LocalDate> get() = _date

    private var _time = MutableLiveData(LocalTime.now().withSecond(0).withNano(0))
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

    fun setMealName(name: String) {
        _mealName.postValue(name)
    }

    fun setDate(date: LocalDate) {
        _date.postValue(date)
    }

    fun setTime(time: LocalTime) {
        _time.postValue(time)
    }

    fun setChosenRecipeId(recipe: Recipe) {
        if (_chosenRecipeId.value != recipe.id) {
            _chosenRecipeId.postValue(recipe.id)
            _chosenRecipeName.postValue(recipe.name)
        } else {
            _chosenRecipeId.postValue(null)
            _chosenRecipeName.postValue("")
        }
    }

    fun saveMeal() { // TODO validation
        GlobalScope.launch {
            val dateTime = LocalDateTime.of(_date.value, _time.value)
            val meal = Meal(mealId!!, dateTime, _mealName.value!!, _chosenRecipeId.value)
            interactors.saveMeal(meal)
        }
    }

    fun setMealDetailsToEdit() {
        GlobalScope.launch {
            val meal = interactors.findMeal(mealId!!)
            setMealName(meal.name)
            setDate(meal.date.toLocalDate())
            setTime(meal.date.toLocalTime())
            meal.recipeId?.let {
                setChosenRecipeId(interactors.findRecipe(it))
            }
        }
    }

}