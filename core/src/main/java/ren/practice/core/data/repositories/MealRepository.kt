package ren.practice.core.data.repositories

import ren.practice.core.data.MealDataSource
import ren.practice.core.domain.Meal
import java.time.LocalDate

class MealRepository(private val dataSource: MealDataSource) {
    suspend fun save(meal: Meal) = dataSource.save(meal)
    suspend fun findAll() = dataSource.findAll()
    suspend fun findById(id: Long) = dataSource.findById(id)
    suspend fun delete(id: Long) = dataSource.delete(id)
    suspend fun findByDate(date: LocalDate) = dataSource.findByDate(date)
}