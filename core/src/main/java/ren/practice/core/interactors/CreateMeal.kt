package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository
import ren.practice.core.domain.Meal

class CreateMeal(private val repository: MealRepository, private val meal: Meal) {

    // TODO implement next interactors, see how it should be
    suspend operator fun invoke() = repository.createMeal(meal)

}