package ren.practice.core.data

import ren.practice.core.domain.Recipe

interface RecipeDataSource {

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun readRecipes(): List<Recipe>
}