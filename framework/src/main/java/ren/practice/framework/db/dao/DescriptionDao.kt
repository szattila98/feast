package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ren.practice.framework.db.entity.DescriptionEntity

@Dao
interface DescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(vararg descriptions: DescriptionEntity)

    @Query("SELECT * FROM description WHERE recipeId = :recipeId")
    suspend fun findAllByRecipeId(recipeId: Long): List<DescriptionEntity>

    @Query("DELETE FROM description WHERE recipeId = :recipeId")
    suspend fun deleteAllByRecipeId(recipeId: Long)
}