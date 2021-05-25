package ren.practice.framework.db.datasource

import android.content.Context
import ren.practice.core.data.RecipeDataSource
import ren.practice.core.domain.Description
import ren.practice.core.domain.Ingredient
import ren.practice.core.domain.Recipe
import ren.practice.framework.db.MealDatabase
import ren.practice.framework.db.dao.DescriptionDao
import ren.practice.framework.db.dao.IngredientDao
import ren.practice.framework.db.dao.MealDao
import ren.practice.framework.db.dao.RecipeDao
import ren.practice.framework.db.entity.DescriptionEntity
import ren.practice.framework.db.entity.IngredientEntity
import ren.practice.framework.db.entity.RecipeEntity
import java.time.LocalDate

class RoomRecipeDataSource(context: Context) : RecipeDataSource {

    private val mealDao: MealDao = MealDatabase.getInstance(context).mealDao()
    private val recipeDao: RecipeDao = MealDatabase.getInstance(context).recipeDao()
    private val ingredientDao: IngredientDao = MealDatabase.getInstance(context).ingredientDao()
    private val descriptionDao: DescriptionDao = MealDatabase.getInstance(context).descriptionDao()

    override suspend fun save(recipe: Recipe): Long {
        val recipeId = recipeDao.save(
            RecipeEntity(
                id = recipe.id,
                name = recipe.name,
                created = recipe.created.toString()
            )
        )
        ingredientDao.deleteAllByRecipeId(recipeId)
        descriptionDao.deleteAllByRecipeId(recipeId)
        ingredientDao.saveAll(*recipe.ingredients.map {
            IngredientEntity(name = it.name, amount = it.amount, unit = it.unit, recipeId = recipeId)
        }.toTypedArray())
        descriptionDao.saveAll(*recipe.description.map {
            DescriptionEntity(text = it.text, recipeId = recipeId)
        }.toTypedArray())
        return recipeId
    }

    override suspend fun findAll() = recipeDao.findAll().map { recipeFromEntity(it) }

    override suspend fun findById(id: Long) = recipeFromEntity(recipeDao.findById(id))

    override suspend fun delete(id: Long): Boolean {
        mealDao.findAll().forEach {
            if (it.recipeId == id) return false
        }
        recipeDao.deleteById(id)
        ingredientDao.deleteAllByRecipeId(id)
        descriptionDao.deleteAllByRecipeId(id)
        return true
    }

    private suspend fun recipeFromEntity(recipeEntity: RecipeEntity) = recipeEntity.let {
        val recipe = Recipe(id = it.id, name = it.name, created = LocalDate.parse(it.created))
        recipe.ingredients.addAll(ingredientDao.findAllByRecipeId(recipe.id).map { entity ->
            Ingredient(entity.name, entity.amount, entity.unit)
        })
        recipe.description.addAll(descriptionDao.findAllByRecipeId(recipe.id).map { entity ->
            Description(entity.text)
        })
        recipe
    }

}