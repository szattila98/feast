package ren.practice.core.data.repositories

import ren.practice.core.data.MealDataSource
import ren.practice.core.domain.Meal
import java.time.LocalDate

class MealRepository(private val dataSource: MealDataSource) {

    suspend fun saveMeal(meal: Meal) = dataSource.saveMeal(meal)

    suspend fun readRelevantMeals(date: LocalDate) = dataSource.readMeals().filter {
        it.date.toLocalDate().isEqual(date)
    }

}