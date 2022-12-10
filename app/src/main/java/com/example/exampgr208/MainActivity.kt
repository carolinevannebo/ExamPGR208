package com.example.exampgr208

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.logic.SearchEngine
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main){

            val recipesRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)

            val apiEndpointQuery = "all"
            val recipeList: ArrayList<RecipeItem> = MainRepository().downloadAssetList(apiEndpointQuery)
            val recipeItemAdapter = RecipeItemAdapter(this, recipeList)

        // tester søkefelt
            SearchEngine(recipeList).onLoad(
                recipeItemAdapter, findViewById<View>(R.id.search_bar) as EditText
            )
        //
            val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            recipesRecyclerView.layoutManager = linearLayoutManager
            recipesRecyclerView.adapter = recipeItemAdapter

        }
    }
}