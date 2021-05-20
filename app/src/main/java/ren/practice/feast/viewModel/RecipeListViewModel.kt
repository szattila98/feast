package ren.practice.feast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ren.practice.core.domain.Recipe
import ren.practice.feast.data.DataSource

class RecipeListViewModel : ViewModel() {

    private var _recipes = MutableLiveData(mutableListOf<Recipe>())
    val recipes: LiveData<MutableList<Recipe>>
        get() = _recipes

    init {
        _recipes.value = DataSource.getRecipes().toMutableList()
    }
}