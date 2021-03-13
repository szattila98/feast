package ren.practice.feast.model

import java.time.LocalDate

data class Meal(
    val id: Int,
    val date: LocalDate,
    val shownName: String,
    val orderNum: Int
    /* val recipe: Recipe, */
)