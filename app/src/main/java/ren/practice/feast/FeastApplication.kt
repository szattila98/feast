package ren.practice.feast

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ren.practice.feast.common.*

class FeastApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@FeastApplication)
            modules(
                listOf(
                    mealRepositoryModule,
                    recipeRepositoryModule,
                    interactors,
                    mealCalendarViewModel,
                    mealEditorViewModel,
                    recipeDetailsViewModel,
                    recipeEditorViewModel,
                    recipeListViewModel
                )
            )
        }
    }
}