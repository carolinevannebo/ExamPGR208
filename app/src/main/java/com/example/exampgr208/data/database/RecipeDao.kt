package com.example.exampgr208.data.database

import androidx.room.*
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.SearchResult

@Dao
interface RecipeDao {

    @Insert
    fun insert(recipe: RecipeItem)

    @Update
    fun update(recipe: RecipeItem)

    @Delete
    fun delete(recipe: RecipeItem)

    @Insert
    fun insertSearchResult(searchResult: SearchResult)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): List<RecipeItem>

    @Query("SELECT * FROM search_results")
    fun getAllSearchResults(): List<SearchResult>

    /*@Query("SELECT * FROM recipe")
    fun getAll(): List<RecipeItem>

    @Query("SELECT * FROM recipe WHERE uri IN (:recipeItemIds)")
    fun loadAllByIds(recipeItemIds: Array<String>): List<RecipeItem>

    @Insert
    fun insertAll(vararg recipeItems: List<RecipeItem>)

    @Delete
    fun delete(RecipeItem: RecipeItem)*/
}