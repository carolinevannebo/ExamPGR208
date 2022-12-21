package com.example.exampgr208.data.database

import androidx.room.*
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.SearchResult

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeItem)

    @Update
    fun update(recipe: RecipeItem)

    @Delete
    fun delete(recipe: RecipeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(searchResult: SearchResult)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): List<RecipeItem>

    @Query("SELECT * FROM search_results")
    fun getAllSearchResults(): List<SearchResult>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun select(id: Int): RecipeItem?

}