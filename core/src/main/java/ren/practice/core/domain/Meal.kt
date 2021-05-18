package ren.practice.core.domain

import java.io.Serializable
import java.time.LocalDateTime

data class Meal(
    val id: Long = 0,
    val date: LocalDateTime,
    val name: String,
    var recipe: Recipe? = null
) : Serializable