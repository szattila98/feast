package ren.practice.feast.viewModel.common

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ren.practice.core.data.repositories.MealRepository
import ren.practice.core.data.repositories.RecipeRepository
import ren.practice.core.interactors.*
import ren.practice.feast.viewModel.*
import ren.practice.framework.Interactors
import ren.practice.framework.db.datasource.RoomMealDataSource
import ren.practice.framework.db.datasource.RoomRecipeDataSource

val mealRepositoryModule = module {
    single {
        MealRepository(RoomMealDataSource(get()))
    }
}

val recipeRepositoryModule = module {
    single {
        RecipeRepository(RoomRecipeDataSource(get()))
    }
}

val interactors = module {
    single {
        Interactors(
            SaveMeal(get()),
            FindAllMeals(get()),
            FindMeal(get()),
            DeleteMeal(get()),
            FindRelevantMeals(get()),
            SaveRecipe(get()),
            FindAllRecipes(get()),
            FindRecipe(get()),
            DeleteRecipe(get()),
            IsRecipeUnrelatedToMeals(get())
        )
    }
}

val mealCalendarViewModel = module {
    viewModel {
        MealCalendarViewModel(get())
    }
}

val mealEditorViewModel = module {
    viewModel {
        MealEditorViewModel(get())
    }
}

val recipeDetailsViewModel = module {
    viewModel {
        RecipeDetailsViewModel(get())
    }
}

val recipeEditorViewModel = module {
    viewModel {
        RecipeEditorViewModel(get())
    }
}

val recipeListViewModel = module {
    viewModel {
        RecipeListViewModel(get())
    }
}
