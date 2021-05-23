package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository

class FindMeal(private val repository: MealRepository) {
    suspend operator fun invoke(id: Long) = repository.findById(id)
}