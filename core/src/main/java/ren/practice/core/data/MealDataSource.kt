package ren.practice.core.data

import ren.practice.core.domain.Meal

interface MealDataSource {

    suspend fun saveMeal(meal: Meal)

    suspend fun readMeals(): List<Meal>

}