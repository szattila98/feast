package ren.practice.core.domain

import java.io.Serializable
import java.time.LocalDate

data class Recipe(
    val id: Long = 0,
    val name: String,
    val ingredients: MutableList<Ingredient> = mutableListOf(),
    val description: MutableList<Description> = mutableListOf(),
    val created: LocalDate = LocalDate.now()
) : Serializable