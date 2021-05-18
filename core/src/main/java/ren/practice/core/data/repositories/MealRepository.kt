package ren.practice.core.data.repositories

import ren.practice.core.data.MealDataSource
import ren.practice.core.domain.Meal
import java.time.LocalDate

class MealRepository(private val dataSource: MealDataSource) {

    private suspend fun readMeals() = dataSource.readMeals()

    suspend fun readRelevantMeals(date: LocalDate) = dataSource.readMeals().filter {
        it.date.toLocalDate().isEqual(date)
    }

    suspend fun createMeal(meal: Meal) = dataSource.createMeal(meal)

}