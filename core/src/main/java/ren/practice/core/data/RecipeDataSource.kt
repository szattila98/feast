package ren.practice.core.data

import ren.practice.core.domain.Recipe

interface RecipeDataSource {
    suspend fun save(recipe: Recipe): Long
    suspend fun findAll(): List<Recipe>
    suspend fun findById(id: Long): Recipe
    suspend fun delete(id: Long): Boolean
}