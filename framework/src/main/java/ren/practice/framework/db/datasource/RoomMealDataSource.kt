package ren.practice.framework.db.datasource

import android.content.Context
import ren.practice.core.data.MealDataSource
import ren.practice.core.domain.Meal
import ren.practice.framework.db.MealDatabase
import ren.practice.framework.db.dao.MealDao
import ren.practice.framework.db.entity.MealEntity
import java.time.LocalDate
import java.time.LocalDateTime

class RoomMealDataSource(context: Context) : MealDataSource {

    private val dao: MealDao = MealDatabase.getInstance(context).mealDao()

    override suspend fun save(meal: Meal) = dao.save(
        MealEntity(
            id = meal.id,
            date = meal.date.toString(),
            name = meal.name,
            recipeId = meal.recipeId
        )
    )

    override suspend fun findAll() = dao.findAll().map {
        Meal(it.id, LocalDateTime.parse(it.date), it.name, it.recipeId)
    }

    override suspend fun findById(id: Long) = dao.findById(id).let {
        Meal(it.id, LocalDateTime.parse(it.date), it.name, it.recipeId)
    }

    override suspend fun delete(id: Long) = dao.deleteById(id)

    override suspend fun findByDate(date: LocalDate) = dao.findAll()
        .filter {
            LocalDateTime.parse(it.date).toLocalDate().isEqual(date)
        }.map {
            Meal(it.id, LocalDateTime.parse(it.date), it.name, it.recipeId)
        }

}