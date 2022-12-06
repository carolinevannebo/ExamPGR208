package com.example.exampgr208.data.database

import androidx.room.*
import com.example.exampgr208.data.ListWithRecipeLists
import com.example.exampgr208.data.RecipeList

@Dao
interface RecipeListDao {
    @Query("SELECT * FROM recipeList")
    fun getAll(): List<RecipeList>

    @Transaction
    @Query("SELECT * FROM recipeList")
    fun getListWithRecipeLists(): List<ListWithRecipeLists>

    @Insert
    fun insertAll(vararg recipeLists: RecipeList)

    @Delete
    fun delete(recipeList: RecipeList)
}