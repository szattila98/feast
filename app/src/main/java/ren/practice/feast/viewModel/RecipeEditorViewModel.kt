package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors
import java.time.LocalDate

class RecipeEditorViewModel(private val interactors: Interactors) : ViewModel() {

    val recipeId = MutableLiveData(0L)
    val recipeName = MutableLiveData("")
    val ingredients = MutableLiveData(mutableListOf<Ingredient>())
    val ingredientName = MutableLiveData("")
    val ingredientAmount = MutableLiveData("")
    val ingredientUnit = MutableLiveData("")
    val descriptionList = MutableLiveData(mutableListOf<Description>())
    val descriptionText = MutableLiveData("")
    var createdDate = MutableLiveData(LocalDate.now())
    val newRecipeId = MutableLiveData<Long>()

    fun addIngredient() {
        ingredients.value?.add(
            Ingredient(ingredientName.value!!, ingredientAmount.value!!, ingredientUnit.value!!)
        )
        ingredientName.value = ""
        ingredientAmount.value = ""
        ingredientUnit.value = ""
    }

    fun addDescriptionRecord() {
        descriptionList.value?.add(
            Description(descriptionText.value!!)
        )
        descriptionText.value = ""
    }

    fun removeIngredient(ingredient: Ingredient) {
        ingredients.value?.let {
            if (it.size > 0) it.remove(ingredient)
        }
    }

    fun removeDescriptionRecord(description: Description) {
        descriptionList.value?.let {
            if (it.size > 0) it.remove(description)
        }
    }

    fun setRecipeDetailsToEdit() = GlobalScope.launch {
        recipeId.value?.let {
            val recipe = interactors.findRecipe(it)
            recipeName.postValue(recipe.name)
            createdDate.postValue(recipe.created)
            ingredients.postValue(recipe.ingredients)
            descriptionList.postValue(recipe.description)
        }
    }

    fun submitRecipe() = GlobalScope.launch {
        if (recipeId.value == 0L) {
            createdDate.postValue(LocalDate.now())
        }
        val id = interactors.saveRecipe(
            Recipe(recipeId.value!!, recipeName.value!!, ingredients.value!!, descriptionList.value!!, createdDate.value!!)
        )
        newRecipeId.postValue(id)
    }
}