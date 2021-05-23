package ren.practice.core.interactors

import ren.practice.core.data.repositories.RecipeRepository

class DeleteRecipe(private val repository: RecipeRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}