package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository
import java.time.LocalDate

class FindRelevantMeals(private val repository: MealRepository) {
    suspend operator fun invoke(date: LocalDate) = repository.findByDate(date)
}