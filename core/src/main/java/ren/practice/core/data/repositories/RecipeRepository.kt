package ren.practice.core.data.repositories

import ren.practice.core.data.RecipeDataSource
import ren.practice.core.domain.Recipe

class RecipeRepository(private val dataSource: RecipeDataSource) {
    suspend fun save(recipe: Recipe) = dataSource.save(recipe)
    suspend fun findAll() = dataSource.findAll()
    suspend fun findById(id: Long) = dataSource.findById(id)
    suspend fun delete(id: Long) = dataSource.delete(id)
}