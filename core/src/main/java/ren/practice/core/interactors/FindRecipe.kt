package ren.practice.core.interactors

import ren.practice.core.data.repositories.RecipeRepository

class FindRecipe(private val repository: RecipeRepository) {
    suspend operator fun invoke(id: Long) = repository.findById(id)
}