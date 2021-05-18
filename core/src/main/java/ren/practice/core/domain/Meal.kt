package ren.practice.core.domain

import java.time.LocalDateTime

data class Meal(
    val id: Long = 0,
    val date: LocalDateTime,
    val name: String,
    private var _recipe: Recipe? = null
) {
    val recipe get() = _recipe

    fun addRecipe(newRecipe: Recipe) {
        _recipe = newRecipe
    }
}