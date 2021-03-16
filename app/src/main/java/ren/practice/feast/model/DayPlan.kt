package ren.practice.feast.model

import java.time.LocalDate

data class DayPlan(val date: LocalDate, val meals: List<Meal>)

data class Meal(
    val id: Int,
    val date: LocalDate,
    val shownName: String,
    val orderNum: Int,
    var recipe: Recipe? = null
) {
    fun addRecipe(newRecipe: Recipe) {
        recipe = newRecipe
    }
}