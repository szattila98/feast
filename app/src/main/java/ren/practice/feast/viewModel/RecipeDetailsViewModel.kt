package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Recipe
import ren.practice.feast.data.DataSource

class RecipeDetailsViewModel : ViewModel() {

    private var _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe>
        get() = _recipe

    fun loadRecipe(id: Long) {
        _recipe.value = DataSource.readRecipe(id)
    }

    fun deleteRecipe(): Boolean {
        _recipe.value?.id?.let {
            val isUnrelated = DataSource.isRecipeUnrelatedToMeals(it)
            if (isUnrelated) {
                DataSource.deleteRecipe(it)
            }
            return isUnrelated
        }
        return false
    }
}