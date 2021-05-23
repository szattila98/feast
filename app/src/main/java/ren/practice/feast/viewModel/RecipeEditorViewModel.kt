package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Recipe
import ren.practice.feast.data.DataSource
import java.time.LocalDate

class RecipeEditorViewModel : ViewModel() {

    private var _recipeId = MutableLiveData(0L)
    var recipeId: Long?
        get() = _recipeId.value
        set(value) {
            _recipeId.value = value
        }

    private var _recipeName = MutableLiveData("")
    val recipeName get() = _recipeName

    private var _ingredients = MutableLiveData(mutableListOf<Ingredient>())
    val ingredients: LiveData<MutableList<Ingredient>>
        get() = _ingredients

    private var _descriptionList = MutableLiveData(mutableListOf<Description>())
    val descriptionList: LiveData<MutableList<Description>>
        get() = _descriptionList

    private var createdDate = LocalDate.now()

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.value?.add(ingredient)
    }

    fun addDescriptionRecord(description: Description) {
        _descriptionList.value?.add(description)
    }

    fun removeIngredient(ingredient: Ingredient) {
        _ingredients.value?.let {
            if (it.size > 0) it.remove(ingredient)
        }
    }

    fun removeDescriptionRecord(description: Description) {
        _descriptionList.value?.let {
            if (it.size > 0) it.remove(description)
        }
    }

    fun submitRecipe(): Long {
        if (recipeId == 0L) {
            createdDate = LocalDate.now()
        }
        return DataSource.saveRecipe(
            Recipe(recipeId!!, _recipeName.value!!, _ingredients.value!!, _descriptionList.value!!, createdDate)
        )
    }

    fun setRecipeDetailsToEdit() {
        val recipe = DataSource.readRecipe(recipeId!!)
        _recipeName.value = recipe.name
        createdDate = recipe.created
        _ingredients.value = recipe.ingredients
        _descriptionList.value = recipe.description
    }
}