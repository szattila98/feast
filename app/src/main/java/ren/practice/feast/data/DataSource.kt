package ren.practice.feast.data

import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Meal
import ren.practice.core.domain.Recipe
import java.time.LocalDate
import java.time.LocalDateTime

object DataSource {

    private val recipes: MutableList<Recipe> =
        mutableListOf(Recipe(1, "Recipe", mutableListOf(), mutableListOf()))

    fun getRecipes(): List<Recipe> {
        return recipes
    }

    fun saveRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    fun readMeals(): List<Meal> {
        val date1 = LocalDateTime.now()
        val date2 = LocalDateTime.now().plusDays(1)
        val date3 = LocalDateTime.now().plusDays(2)
        val date4 = LocalDateTime.now().plusDays(3)
        val date6 = LocalDateTime.now().plusDays(5)
        val date7 = LocalDateTime.now().plusDays(6)

        val recipe = Recipe(
            1,
            "Recept",
            mutableListOf(Ingredient("Hozzávaló", "12", "kg")),
            mutableListOf(
                Description("Tedd bele a tejet!")
            )
        )

        return listOf(
            Meal(1, date1, "Kaja1", recipe),
            Meal(2, date1, "Kaja1alt"),
            Meal(3, date2, "Kaja1", recipe),
            Meal(4, date3, "Kaja1", recipe),
            Meal(5, date4, "Kaja1", recipe),
            Meal(6, date6, "Kaja1", recipe),
            Meal(7, date7, "Kaja1", recipe),
        )
    }

    fun readRelevantMeals(date: LocalDate): List<Meal> {
        return readMeals().filter {
            it.date.toLocalDate().isEqual(date)
        }
    }

    fun readRecipe(id: Long): Recipe {
        return recipes.find { it.id == id }!!
    }
}