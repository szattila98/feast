package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository
import ren.practice.core.domain.Meal

class CreateMeal(private val repository: MealRepository) {
    suspend operator fun invoke(meal: Meal) = repository.createMeal(meal)
}