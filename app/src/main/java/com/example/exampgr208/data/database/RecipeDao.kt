package com.example.exampgr208.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.exampgr208.logic.models.RecipeItem
import com.example.exampgr208.logic.models.RecipeWithResults
import com.example.exampgr208.logic.models.SearchResult

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

    @Query("SELECT search_query FROM search_results")
    fun getAllSearchQueries(): List<String>

    /*@Query("SELECT result FROM search_results")
    fun getAllSearchResultLists(): List<*>*/   //  -- error: The columns returned by the query does not have the fields [id,isFavorite] in com.example.exampgr208.logic.models.RecipeItem even though they are annotated as non-null or primitive. Columns returned by the query: [result]

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun select(id: Int): RecipeItem?

    @Query("SELECT * FROM recipes WHERE is_favorite = :isFavorite")
    fun select(isFavorite: Boolean): List<RecipeItem>?

    /*@Query("SELECT * FROM search_results WHERE search_query = :query")
    fun select(query: String): LiveData<List<RecipeItem>>*/

    //@Query("SELECT result FROM search_results WHERE search_query = :query")
    //fun select(query: String): LiveData<List<RecipeItem>>

   /* @Transaction
    @Query("SELECT * FROM search_results WHERE search_query = :query")
    fun selectWithResults(query: String): List<RecipeWithResults>
*/
    //    fun select(query: String): List<RecipeWithResults>
    //@Query("SELECT * FROM recipes WHERE id IN (SELECT result FROM search_results WHERE search_query = :query)")
    //fun select(query: String): List<RecipeItem>
    //    fun select(query: String): List<RecipeWithResults>


}