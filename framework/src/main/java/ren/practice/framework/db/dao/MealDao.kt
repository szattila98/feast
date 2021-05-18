package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import ren.practice.framework.db.entity.MealEntity

@Dao
interface MealDao {

    @Insert(onConflict = REPLACE)
    suspend fun add(meal: MealEntity)

    @Query("SELECT * FROM meal")
    suspend fun findAll(): List<MealEntity>

}