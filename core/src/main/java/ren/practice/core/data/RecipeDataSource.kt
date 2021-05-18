package ren.practice.core.data

import ren.practice.core.domain.Recipe

interface RecipeDataSource {
    suspend fun createRecipe(recipe: Recipe): Recipe
}