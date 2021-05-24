package ren.practice.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ren.practice.framework.db.dao.DescriptionDao
import ren.practice.framework.db.dao.IngredientDao
import ren.practice.framework.db.dao.MealDao
import ren.practice.framework.db.dao.RecipeDao
import ren.practice.framework.db.entity.DescriptionEntity
import ren.practice.framework.db.entity.IngredientEntity
import ren.practice.framework.db.entity.MealEntity
import ren.practice.framework.db.entity.RecipeEntity

@Database(
    entities = [MealEntity::class, RecipeEntity::class, IngredientEntity::class, DescriptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun descriptionDao(): DescriptionDao

    companion object {
        private const val DATABASE_NAME = "feast.db"
        private var instance: MealDatabase? = null

        private fun create(context: Context) =
            Room.databaseBuilder(context, MealDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context) = (instance ?: create(context).also { instance = it })
    }
}