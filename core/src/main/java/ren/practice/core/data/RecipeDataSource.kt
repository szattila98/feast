package ren.practice.core.data

import ren.practice.core.domain.Recipe

interface RecipeDataSource {

    suspend fun readRecipes(): List<Recipe>

    suspend fun createRecipe(recipe: Recipe): Recipe

}