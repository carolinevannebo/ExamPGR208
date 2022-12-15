package com.example.exampgr208.data.database

import androidx.room.*
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList

@Dao
interface RecipeListDao {
    @Query("SELECT * FROM recipeList")
    fun getAll(): List<RecipeList>

    @Transaction
    @Query("SELECT * FROM recipeList")
    fun getListWithRecipeLists(): List<ListWithRecipeLists>

    @Query("SELECT * FROM recipeList WHERE Id IN (:recipeListIds)")
    fun loadAllByIds(recipeListIds: IntArray): List<RecipeItem>

    @Insert
    fun insertAll(vararg recipeLists: List<RecipeList>)

    @Delete
    fun delete(recipeList: RecipeList)
}