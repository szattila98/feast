package ren.practice.core.data

import ren.practice.core.domain.Meal
import java.time.LocalDate

interface MealDataSource {
    suspend fun save(meal: Meal)
    suspend fun findAll(): List<Meal>
    suspend fun findById(id: Long): Meal
    suspend fun delete(id: Long)
    suspend fun findByDate(date: LocalDate): List<Meal>
}