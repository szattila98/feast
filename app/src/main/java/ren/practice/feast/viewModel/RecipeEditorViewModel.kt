package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Recipe
import ren.practice.feast.data.DataSource

class RecipeEditorViewModel : ViewModel() {

    private var _ingredients = MutableLiveData(mutableListOf<Ingredient>())
    val ingredients: MutableLiveData<MutableList<Ingredient>>
        get() = _ingredients

    private var _descriptionList = MutableLiveData(mutableListOf<Description>())
    val descriptionList: MutableLiveData<MutableList<Description>>
        get() = _descriptionList

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.value?.add(ingredient)
    }

    fun addDescriptionRecord(description: Description) {
        _descriptionList.value?.add(description)
    }

    fun submitRecipe(recipe: Recipe) {
        DataSource.saveRecipe(recipe)
    }
}