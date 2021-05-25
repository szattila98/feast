package ren.practice.feast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ren.practice.core.domain.Recipe
import ren.practice.framework.Interactors

class RecipeListViewModel(private val interactors: Interactors) : ViewModel() {

    val recipes = MutableLiveData(mutableListOf<Recipe>()).apply {
        GlobalScope.launch {
            postValue(interactors.findAllRecipes().toMutableList())
        }
    }
}