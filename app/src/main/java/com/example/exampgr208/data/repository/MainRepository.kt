package com.example.exampgr208.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.example.exampgr208.data.RecipeItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainRepository {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun downloadAssetList(): ArrayList<RecipeItem> {
        val allHits = ArrayList<RecipeItem>()

        GlobalScope.async {
            val assetData = URL("https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=all").readText()//.toString()

            val assetHitsArray = (JSONObject(assetData).get("hits") as JSONArray)
            println("test" + assetHitsArray.length())
            Log.i("test", assetHitsArray.toString())
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
                val image = BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.size)

                hitsItem.image = image
                hitsItem.source = recipe.getString("source")
                hitsItem.url = recipe.getString("url")
                hitsItem.yield = recipe.getInt("yield")
                val dietLabelsArray = recipe.getJSONArray("dietLabels")
                for (i in 0 until dietLabelsArray.length()) {
                    hitsItem.dietLabels = dietLabelsArray.getString(i)
                    //var string = ""
                    //string += dietLabelsArray.getString(i)
                    //hitsItem.dietLabels = string
                }
                //hitsItem.healthLabels = (recipe).getJSONArray("healthLabels")
                //hitsItem.cautions = (recipe).getJSONArray("cautions")
                //hitsItem.ingredientLines = (recipe).getJSONArray("ingredientLines")

                val mealTypeArray = recipe.getJSONArray("mealType")
                for (i in 0 until mealTypeArray.length()) {
                    hitsItem.mealType = mealTypeArray.getString(i)
                }

                hitsItem.calories = recipe.getInt("calories")

                allHits.add(hitsItem)
            }
        }.await()
        return allHits
    }
}