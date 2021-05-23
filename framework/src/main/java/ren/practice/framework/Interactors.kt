package ren.practice.framework

import ren.practice.core.interactors.*

data class Interactors(
    val saveMeal: SaveMeal,
    val findAllMeals: FindAllMeals,
    val findMeal: FindMeal,
    val deleteMeal: DeleteMeal,
    val findRelevantMeals: FindRelevantMeals,

    val saveRecipe: SaveRecipe,
    val findAllRecipes: FindAllRecipes,
    val findRecipe: FindRecipe,
    val deleteRecipe: DeleteRecipe,
    val isRecipeUnrelatedToMeals: IsRecipeUnrelatedToMeals
)