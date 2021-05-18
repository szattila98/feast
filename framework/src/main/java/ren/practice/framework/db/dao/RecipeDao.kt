package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ren.practice.framework.db.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun findById(id: Long): RecipeEntity

    @Query("SELECT * FROM recipe")
    suspend fun findAll(): List<RecipeEntity>
}