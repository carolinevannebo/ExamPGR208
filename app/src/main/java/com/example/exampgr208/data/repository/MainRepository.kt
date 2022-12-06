package com.example.exampgr208.data.repository

import com.example.exampgr208.data.RecipeList
import com.example.exampgr208.data.repository.remote.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository( private val webService: WebService) {
    companion object {
        const val URL = "https://api.edamam.com/api/recipes/v2"
        const val KEY = "?app_key=2ecd749eade96f92c4303affe954eb31"
        const val ID = "&app_id=8efed005"
        const val TEST_EXAMPLE_URL = "https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=cake"
    }

    suspend fun getRecipes(enableTest: Boolean) : RecipeList
    = withContext(Dispatchers.IO) {
        if (enableTest) {
            repeat(10) {
                webService.getRecipes()
            }
        }
        return@withContext webService.getRecipes()
    }
}