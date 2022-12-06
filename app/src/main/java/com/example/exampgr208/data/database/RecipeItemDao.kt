package com.example.exampgr208.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.exampgr208.data.RecipeItem

@Dao
interface RecipeItemDao {
    @Query("SELECT * FROM recipeItem")
    fun getAll(): List<RecipeItem>

    @Insert
    fun insertAll(vararg recipeItems: RecipeItem)

    @Delete
    fun delete(recipeItem: RecipeItem)
}