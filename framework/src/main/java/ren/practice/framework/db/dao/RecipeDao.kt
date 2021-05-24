package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ren.practice.framework.db.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(recipe: RecipeEntity): Long

    @Query("SELECT * FROM recipe")
    suspend fun findAll(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun findById(id: Long): RecipeEntity

    @Query("DELETE FROM recipe WHERE id = :id")
    suspend fun deleteById(id: Long)
}