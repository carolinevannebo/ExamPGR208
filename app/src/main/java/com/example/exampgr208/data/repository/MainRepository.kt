package com.example.exampgr208.data.repository

import com.example.exampgr208.data.RecipeItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainRepository {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun downloadAssetList(apiEndpointQuery: String): ArrayList<RecipeItem> {
        val allHits = ArrayList<RecipeItem>()

        GlobalScope.async {
            val assetData = URL("https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=$apiEndpointQuery").readText()
            val assetHitsArray = (JSONObject(assetData).get("hits") as JSONArray)
            (0 until assetHitsArray.length()).forEach{ item ->
                val hitsItem = RecipeItem()
                val assetItem = assetHitsArray.get(item)
                val recipe = (assetItem as JSONObject).get("recipe")

                hitsItem.uri = (recipe as JSONObject).getString("uri")
                hitsItem.label = (recipe).getString("label")

                val imgByteArray = URL((((recipe)
                    .get("images") as JSONObject)
                    .get("REGULAR") as JSONObject)
                    .getString("url")).readBytes()

                hitsItem.image = imgByteArray
                hitsItem.source = recipe.getString("source")
                hitsItem.url = recipe.getString("url")
                hitsItem.yield = recipe.getInt("yield")

                val dietLabelsArray = recipe.getJSONArray("dietLabels")
                setDietLabels(hitsItem, dietLabelsArray)

                val healthLabelsArray = recipe.getJSONArray("healthLabels")
                setHealthLabels(hitsItem, healthLabelsArray)

                val cautionsArray = (recipe).getJSONArray("cautions")
                setCautions(hitsItem, cautionsArray)

                val ingredientLinesArray = (recipe).getJSONArray("ingredientLines")
                setIngredientLines(hitsItem, ingredientLinesArray)

                val mealTypeArray = recipe.getJSONArray("mealType")
                setMealType(hitsItem, mealTypeArray)

                hitsItem.calories = recipe.getInt("calories")

                allHits.add(hitsItem)
            }
        }.await()
        return allHits
    }

    private fun setDietLabels(hitsItem: RecipeItem, inputArray: JSONArray) {
        for (i in 0 until inputArray.length()) {
            hitsItem.dietLabels = inputArray.getString(i)
        }
    }

    private fun setHealthLabels(hitsItem: RecipeItem, inputArray: JSONArray){
        for (i in 0 until inputArray.length()) {
            hitsItem.healthLabels = inputArray.getString(i)
        }
    }

    private fun setCautions(hitsItem: RecipeItem, inputArray: JSONArray){
        for (i in 0 until inputArray.length()) {
            hitsItem.cautions = inputArray.getString(i)
        }
    }

    private fun setIngredientLines(hitsItem: RecipeItem, inputArray: JSONArray){
        for (i in 0 until inputArray.length()) {
            hitsItem.ingredientLines = inputArray.getString(i)
        }
    }

    private fun setMealType(hitsItem: RecipeItem, inputArray: JSONArray) {
        for (i in 0 until inputArray.length()) {
            hitsItem.mealType = inputArray.getString(i)
        }
    }

}