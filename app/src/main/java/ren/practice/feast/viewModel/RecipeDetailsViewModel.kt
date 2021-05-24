package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors

class RecipeDetailsViewModel(private val interactors: Interactors) : ViewModel() {

    private var _recipe = MutableLiveData(Recipe(name = ""))
    val recipe: LiveData<Recipe>
        get() = _recipe

    fun loadRecipe(id: Long) {
        GlobalScope.launch {
            _recipe.postValue(interactors.findRecipe(id))
        }
    }

    suspend fun deleteRecipe(): Boolean {
        _recipe.value?.id?.let {
            val isUnrelated = interactors.isRecipeUnrelatedToMeals(it)
            if (isUnrelated) {
                interactors.deleteRecipe(it)
            }
            return isUnrelated
        }
        return false
    }
}