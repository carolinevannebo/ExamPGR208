package com.example.exampgr208

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main){
            //MainRepository().downloadAssetList()

            val recipesRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)

            val recipeList: ArrayList<RecipeItem> = MainRepository().downloadAssetList()
            val recipeItemAdapter = RecipeItemAdapter(this, recipeList)
            val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            recipesRecyclerView.layoutManager = linearLayoutManager
            recipesRecyclerView.adapter = recipeItemAdapter

        }

        /*val recipesRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)

        val recipeList: ArrayList<RecipeItem> = ArrayList<RecipeItem>()
        recipeList.add(0, RecipeItem("", "test", "", "", 4, "Dinner", 800)) //generisk data for å teste at views fungerer
        recipeList.add(1, RecipeItem("", "test2", "", "", 2, "Dinner", 800))

        val recipeItemAdapter = RecipeItemAdapter(this, recipeList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recipesRecyclerView.layoutManager = linearLayoutManager
        recipesRecyclerView.adapter = recipeItemAdapter*/

    }

}