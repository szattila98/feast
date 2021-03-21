package ren.practice.feast.model

import java.time.LocalDate

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: MutableList<Ingredient> = mutableListOf(),
    val description: MutableList<DescriptionRecord> = mutableListOf(),
    val created: LocalDate = LocalDate.now()
)

data class Ingredient(val name: String, val amount: String, val unit: String)

data class DescriptionRecord(val text: String)