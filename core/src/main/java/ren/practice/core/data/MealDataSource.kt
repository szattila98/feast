package ren.practice.core.data

import ren.practice.core.domain.Meal

interface MealDataSource {

    suspend fun readMeals(): List<Meal>

    suspend fun createMeal(meal: Meal): Meal

}