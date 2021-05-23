package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository

class FindAllMeals(private val repository: MealRepository) {
    suspend operator fun invoke() = repository.findAll()
}