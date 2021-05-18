package ren.practice.core.interactors

import ren.practice.core.data.repositories.RecipeRepository

class ReadRecipes(private val repository: RecipeRepository) {
    suspend operator fun invoke() = repository.readRecipes()
}