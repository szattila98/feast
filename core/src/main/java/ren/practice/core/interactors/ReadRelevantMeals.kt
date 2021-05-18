package ren.practice.core.interactors

import ren.practice.core.data.repositories.MealRepository
import java.time.LocalDate

class ReadRelevantMeals(private val repository: MealRepository) {
    suspend operator fun invoke(date: LocalDate) = repository.readRelevantMeals(date)
}