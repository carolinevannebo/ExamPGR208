package com.example.exampgr208.data.repository.remote

import com.example.exampgr208.data.RecipeList
import retrofit2.http.GET

interface Api {
    @GET("recipeList.php")
    suspend fun getRecipes(): RecipeList
}