package ren.practice.core.interactors

import ren.practice.core.data.repositories.RecipeRepository

class FindAllRecipes(private val repository: RecipeRepository) {
    suspend operator fun invoke() = repository.findAll()
}