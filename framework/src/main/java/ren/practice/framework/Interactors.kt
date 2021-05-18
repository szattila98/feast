package ren.practice.framework

import ren.practice.core.interactors.ReadRecipes
import ren.practice.core.interactors.ReadRelevantMeals
import ren.practice.core.interactors.SaveMeal
import ren.practice.core.interactors.SaveRecipe

data class Interactors(
    val saveMeal: SaveMeal,
    val saveRecipe: SaveRecipe,
    val readRecipes: ReadRecipes,
    val readRelevantMeals: ReadRelevantMeals
)