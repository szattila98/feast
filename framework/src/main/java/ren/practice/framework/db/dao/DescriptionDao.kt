package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ren.practice.framework.db.entity.DescriptionEntity
import ren.practice.framework.db.entity.RecipeEntity

@Dao
interface DescriptionDao {

    @Insert(onConflict = REPLACE)
    suspend fun addAll(vararg recipes: RecipeEntity)

    @Query("SELECT * FROM description WHERE recipeId = :recipeId")
    suspend fun findAllByRecipeId(recipeId: Long): List<DescriptionEntity>
}