package ren.practice.core.interactors

import ren.practice.core.data.repositories.RecipeRepository
import ren.practice.core.domain.Recipe

class SaveRecipe(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) = repository.save(recipe)
}