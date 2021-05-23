package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository

class DeleteMeal(private val repository: MealRepository) {
    suspend operator fun invoke(id: Long) = repository.delete(id)
}