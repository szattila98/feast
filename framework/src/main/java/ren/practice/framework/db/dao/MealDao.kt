package ren.practice.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ren.practice.framework.db.entity.MealEntity

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(meal: MealEntity)

    @Query("SELECT * FROM meal")
    suspend fun findAll(): List<MealEntity>

    @Query("SELECT * FROM meal WHERE id = :id")
    suspend fun findById(id: Long): MealEntity

    @Query("DELETE FROM meal WHERE id = :id")
    suspend fun deleteById(id: Long)
}