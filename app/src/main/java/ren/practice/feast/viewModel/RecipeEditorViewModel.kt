package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.feast.data.DataSource
import ren.practice.feast.model.DescriptionRecord
import ren.practice.feast.model.Ingredient
import ren.practice.feast.model.Recipe

class RecipeEditorViewModel : ViewModel() {

    private var _ingredients = MutableLiveData(mutableListOf<Ingredient>())
    val ingredients: MutableLiveData<MutableList<Ingredient>>
        get() = _ingredients

    private var _descriptionList = MutableLiveData(mutableListOf<DescriptionRecord>())
    val descriptionList: MutableLiveData<MutableList<DescriptionRecord>>
        get() = _descriptionList

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.value?.add(ingredient)
    }

    fun addDescriptionRecord(descriptionRecord: DescriptionRecord) {
        _descriptionList.value?.add(descriptionRecord)
    }

    fun submitRecipe(recipe: Recipe) {
        DataSource.saveRecipe(recipe)
    }
}