package ren.practice.core.domain

import java.time.LocalDateTime

data class Meal(
    val id: Long = 0,
    val date: LocalDateTime,
    val name: String,
    var recipeId: Long? = null
)