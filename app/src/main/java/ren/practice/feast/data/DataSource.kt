package ren.practice.feast.data

import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Meal
import ren.practice.core.domain.Recipe
import java.time.LocalDate
import java.time.LocalDateTime

object DataSource {

    private val recipes: MutableList<Recipe> =
        mutableListOf(
            Recipe(
                1,
                "Test Recept Név",
                mutableListOf(
                    Ingredient("Hozzávaló", "12", "kg")
                ),
                mutableListOf(
                    Description("Tedd bele a tejet!")
                )
            )
        )

    private val meals = mutableListOf<Meal>()

    init {
        val date1 = LocalDateTime.now()
        val date2 = LocalDateTime.now().plusDays(1)
        val date3 = LocalDateTime.now().plusDays(2)
        val date4 = LocalDateTime.now().plusDays(3)
        val date6 = LocalDateTime.now().plusDays(5)
        val date7 = LocalDateTime.now().plusDays(6)

        meals.addAll(
            listOf(
                Meal(1, date1, "Kaja1", 1),
                Meal(2, date1, "Kaja1alt"),
                Meal(3, date2, "Kaja1", 1),
                Meal(4, date3, "Kaja1", 1),
                Meal(5, date4, "Kaja1", 1),
                Meal(6, date6, "Kaja1", 1),
                Meal(7, date7, "Kaja1", 1),
            )
        )
    }

    fun getRecipes(): List<Recipe> {
        return recipes
    }

    fun saveRecipe(recipe: Recipe): Long {
        val recipe = Recipe((1L..1000L).random(), recipe.name, recipe.ingredients, recipe.description, recipe.created)
        recipes.add(recipe)
        return recipe.id
    }

    fun readMeals(): List<Meal> {
        return meals
    }

    fun readRelevantMeals(date: LocalDate): List<Meal> {
        return readMeals().filter {
            it.date.toLocalDate().isEqual(date)
        }
    }

    fun readRecipe(id: Long): Recipe {
        return recipes.find { it.id == id }!!
    }

    fun saveMeal(meal: Meal) {
        if (meal.id != 0L) {
            deleteMeal(meal.id)
            meals.add(meal)
            return
        }
        meals.add(Meal((1L..1000L).random(), meal.date, meal.name, meal.recipeId))
    }

    fun deleteMeal(id: Long) {
        meals.removeIf { it.id == id }
    }

    fun readMeal(id: Long) = meals.find { it.id == id }!!

}