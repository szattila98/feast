package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ren.practice.framework.db.entity.IngredientEntity

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(vararg ingredients: IngredientEntity)

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    suspend fun findAllByRecipeId(recipeId: Long): List<IngredientEntity>

    @Query("DELETE FROM ingredient WHERE recipeId = :recipeId")
    suspend fun deleteAllByRecipeId(recipeId: Long)
}