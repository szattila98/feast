package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors

class RecipeListViewModel(private val interactors: Interactors) : ViewModel() {

    private var _recipes = MutableLiveData(mutableListOf<Recipe>()).apply {
        GlobalScope.launch {
            postValue(interactors.findAllRecipes().toMutableList())
        }
    }
    val recipes: LiveData<MutableList<Recipe>>
        get() = _recipes
}