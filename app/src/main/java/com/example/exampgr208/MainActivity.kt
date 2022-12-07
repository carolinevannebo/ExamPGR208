package com.example.exampgr208

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.exampgr208.data.repository.MainRepository
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main){
            MainRepository().downloadAssetList()
        }
        /*val assetHitsArray = (JSONObject(MainRepository.URL).get("hits") as JSONArray)

        runBlocking{
            println("test" + assetHitsArray.length())
        }*/
    }

    suspend fun myFun() {
        GlobalScope.async {
            val assetData = URL("https://api.edamam.com/api/recipes/v2?app_key=2ecd749eade96f92c4303affe954eb31&app_id=8efed005&type=public&q=all").readText()
            val assetDataArray = (JSONObject(assetData.toString()).get("hits") as JSONArray)
            Log.i("testing", assetDataArray.toString())
        }.await()
    }

    /*@OptIn(DelicateCoroutinesApi::class)
    suspend fun myFun() {
        GlobalScope.async {
            val assetHitsArray = (JSONObject(MainRepository.URL).get("hits") as JSONArray)
            println("test" + assetHitsArray.length())
        }
    }*/

}