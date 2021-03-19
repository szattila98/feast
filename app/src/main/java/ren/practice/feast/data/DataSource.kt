package ren.practice.feast.data

import ren.practice.feast.model.*
import java.time.LocalDate

object DataSource {

    private val recipes: MutableList<Recipe> = mutableListOf()

    fun getRecipes(): MutableList<Recipe> {
        return recipes
    }

    fun saveRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    fun readDayPlan(): List<DayPlan> {
        val date1 = LocalDate.now()
        val date2 = LocalDate.now().plusDays(1)
        val date3 = LocalDate.now().plusDays(2)
        val date4 = LocalDate.now().plusDays(3)
        val date5 = LocalDate.now().plusDays(4)
        val date6 = LocalDate.now().plusDays(5)
        val date7 = LocalDate.now().plusDays(6)

        val recipe = Recipe(
            1,
            "Recept",
            mutableListOf(Ingredient("Hozzávaló", "12", "kg")),
            mutableListOf(
                DescriptionRecord("Tedd bele a tejet!")
            )
        )

        return listOf(
            DayPlan(
                date1, listOf(
                    Meal(1, date1, "Kaja1", 1),
                    Meal(2, date1, "Kaja1", 2, recipe),
                    Meal(3, date1, "Kaja1", 3, recipe),
                    Meal(4, date1, "Kaja1", 4, recipe),
                    Meal(5, date1, "Kaja1", 5, recipe),
                )
            ),
            DayPlan(
                date2, listOf(
                    Meal(6, date2, "Kaja2", 1, recipe),
                    Meal(7, date2, "Kaja2", 2, recipe),
                    Meal(8, date2, "Kaja2", 3, recipe),
                    Meal(9, date2, "Kaja2", 4, recipe),
                    Meal(10, date2, "Kaja2", 5, recipe),
                )
            ),
            DayPlan(
                date3, listOf(
                    Meal(11, date3, "Kaja3", 1, recipe),
                    Meal(12, date3, "Kaja3", 2, recipe),
                    Meal(13, date3, "Kaja3", 3, recipe),
                    Meal(14, date3, "Kaja3", 4, recipe),
                    Meal(15, date3, "Kaja3", 5, recipe),
                )
            ),
            DayPlan(
                date4, listOf(
                    Meal(16, date4, "Kaja4", 1, recipe),
                    Meal(17, date4, "Kaja4", 2, recipe),
                    Meal(18, date4, "Kaja4", 3, recipe),
                    Meal(19, date4, "Kaja4", 4, recipe),
                    Meal(20, date4, "Kaja4", 5, recipe),
                )
            ),
            DayPlan(
                date5, listOf(
                    Meal(17, date5, "Kaja5", 2, recipe),
                    Meal(16, date5, "Kaja5", 1, recipe),
                    Meal(18, date5, "Kaja5", 3, recipe),
                    Meal(19, date5, "Kaja5", 4, recipe),
                    Meal(20, date5, "Kaja5", 5, recipe),
                )
            ),
            DayPlan(
                date6, listOf(
                    Meal(16, date6, "Kaja6", 1, recipe),
                    Meal(17, date6, "Kaja6", 2, recipe),
                    Meal(18, date6, "Kaja6", 3, recipe),
                    Meal(19, date6, "Kaja6", 4, recipe),
                    Meal(20, date6, "Kaja6", 5, recipe),
                )
            ),
            DayPlan(
                date7, listOf(
                    Meal(16, date7, "Kaja7", 1, recipe),
                    Meal(17, date7, "Kaja7", 2, recipe),
                    Meal(18, date7, "Kaja7", 3, recipe),
                    Meal(19, date7, "Kaja7", 4, recipe),
                    Meal(20, date7, "Kaja7", 5, recipe),
                )
            ),
        )
    }
}