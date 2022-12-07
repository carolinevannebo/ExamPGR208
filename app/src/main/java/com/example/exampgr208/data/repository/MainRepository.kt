package com.example.exampgr208.data.repository

import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList
import com.example.exampgr208.data.repository.remote.WebService
import com.squareup.moshi.Json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainRepository/*( private val webService: WebService)*/ {
    /*companion object {
        const val URL: String = "https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=all"
        //const val TEST_EXAMPLE_URL = "https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=cake"
    }*/
    /*
    suspend fun getRecipes(enableTest: Boolean) : RecipeList
    = withContext(Dispatchers.IO) {
        if (enableTest) {
            repeat(10) {
                webService.getRecipes()
            }
        }
        return@withContext webService.getRecipes()
    }*/

    //enten IRecipe eller RecipeItem
    suspend fun downloadAssetList(): ArrayList<RecipeItem> {
        val allHits = ArrayList<RecipeItem>()

        GlobalScope.async {
            val assetData = URL("https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=all").readText()//.toString()
            //val assetDataToString = assetData.toString()
            val assetHitsArray = (JSONObject(assetData).get("hits") as JSONArray)
            println("test" + assetHitsArray.length())
            (0 until assetHitsArray.length()).forEach{ item ->
                val hitsItem = RecipeItem()
                val assetItem = assetHitsArray.get(item)
                //val recipe = (assetItem as JSONObject).getJSONObject("recipe")
                val recipe = (assetItem as JSONObject).get("recipe")

                hitsItem.uri = (recipe as JSONObject).getString("uri")
                hitsItem.label = recipe.getString("label")
                hitsItem.source = recipe.getString("source")
                hitsItem.url = recipe.getString("url")
                hitsItem.mealType = recipe.getString("mealType")

                //hitsItem.assetUri = (recipe as JSONObject).getString("uri")
                //hitsItem.assetLabel = (recipe as JSONObject).getString("label")
                //hitsItem.assetSource = (recipe as JSONObject).getString("source")
                //hitsItem.assetUrl = (recipe as JSONObject).getString("url")
                //hitsItem.assetMealType = (recipe as JSONObject).getString("mealType")

                //hitsItem.uri = recipe.getString("uri")
                //hitsItem.label = recipe.getString("label")
                //hitsItem.source = recipe.getString("source")
                //hitsItem.url = recipe.getString("url")
                //hitsItem.mealType = recipe.getString("mealType")

                allHits.add(hitsItem)
            }
        }.await()
        return allHits
    }
}