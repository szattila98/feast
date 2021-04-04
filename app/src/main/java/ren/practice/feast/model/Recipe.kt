package ren.practice.feast.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Meal(
    val id: Int,
    val date: LocalDate,
    val shownName: String,
    val orderNum: Int,
    var recipe: Recipe? = null
) : Parcelable {
    fun addRecipe(newRecipe: Recipe) {
        recipe = newRecipe
    }
}

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: MutableList<Ingredient> = mutableListOf(),
    val description: MutableList<DescriptionRecord> = mutableListOf(),
    val created: LocalDate = LocalDate.now()
) : Parcelable

@Parcelize
data class Ingredient(val name: String, val amount: String, val unit: String) : Parcelable

@Parcelize
data class DescriptionRecord(val text: String) : Parcelable