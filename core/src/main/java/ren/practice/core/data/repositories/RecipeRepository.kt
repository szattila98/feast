package ren.practice.core.data.repositories

import ren.practice.core.data.RecipeDataSource
import ren.practice.core.domain.Recipe

class RecipeRepository(private val dataSource: RecipeDataSource) {

    suspend fun readRecipes() = dataSource.readRecipes()

    suspend fun createRecipe(recipe: Recipe) = dataSource.createRecipe(recipe)

}