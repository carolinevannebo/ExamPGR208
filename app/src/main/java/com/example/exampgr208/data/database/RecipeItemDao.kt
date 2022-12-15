package com.example.exampgr208.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.exampgr208.data.RecipeItem

@Dao
interface RecipeItemDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<RecipeItem>

    @Query("SELECT * FROM recipe WHERE uri IN (:recipeItemIds)")
    fun loadAllByIds(recipeItemIds: Array<String>): List<RecipeItem>

    @Insert
    fun insertAll(vararg recipeItems: List<RecipeItem>)

    @Delete
    fun delete(RecipeItem: RecipeItem)
}