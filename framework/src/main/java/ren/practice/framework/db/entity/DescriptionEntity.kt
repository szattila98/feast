package ren.practice.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "description")
data class DescriptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val recipeId: Long
)