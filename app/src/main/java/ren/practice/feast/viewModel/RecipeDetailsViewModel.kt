package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors

class RecipeDetailsViewModel(private val interactors: Interactors) : ViewModel() {

    val recipe = MutableLiveData(Recipe(name = ""))
    val deleteSuccessful = MutableLiveData<Boolean>()

    fun loadRecipe(id: Long) = GlobalScope.launch {
        recipe.postValue(interactors.findRecipe(id))
    }

    fun deleteRecipe() = GlobalScope.launch {
        recipe.value?.id?.let {
            val isSuccessful = interactors.deleteRecipe(it)
            deleteSuccessful.postValue(isSuccessful)
            return@launch
        }
        deleteSuccessful.postValue(false)
    }
}