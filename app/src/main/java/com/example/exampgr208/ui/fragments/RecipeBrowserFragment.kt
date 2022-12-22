package com.example.exampgr208.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.logic.models.RecipeItem
import com.example.exampgr208.logic.models.SearchResult
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.logic.interfaces.OnItemCheckListener
import com.example.exampgr208.logic.interfaces.OnItemClickListener
import com.example.exampgr208.ui.adapters.RecipeItemAdapter
import kotlinx.coroutines.*
import java.io.FileNotFoundException

class RecipeBrowserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var newArrayList : ArrayList<RecipeItem>
    lateinit var tempArrayList : ArrayList<RecipeItem>
    private var apiEndpointQuery = "all"
    lateinit var database : RecipeDatabase
    lateinit var recipeDao : RecipeDao
    private lateinit var searchView : androidx.appcompat.widget.SearchView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_browser_fragment, container, false)

        val application = requireNotNull(this.activity).application
        database = DatabaseSingleton.getInstance(application)
        recipeDao = database.recipeDao()

        recyclerView = view.findViewById(R.id.recyclerview_main)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()

        GlobalScope.launch {
            newArrayList = MainRepository().downloadAssetList(apiEndpointQuery).take(20) as ArrayList<RecipeItem>
            tempArrayList.addAll(newArrayList)


            for (i in tempArrayList.indices) {
                val currentRecipe = tempArrayList[i]
                currentRecipe.id = i + 1
                Log.i("id", currentRecipe.id.toString())
            }

            withContext(Dispatchers.Main){
                val adapter = RecipeItemAdapter(this, tempArrayList, recipeDao)
                recyclerView.adapter = adapter

                registerAdapterOnClick(view, adapter)
                registerAdapterOnCheck(adapter)

            }
        }

        searchView = view.findViewById(R.id.search_bar)
        searchEngine(view)

        return view
    }

    private fun registerAdapterOnClick(view: View, adapter: RecipeItemAdapter) { //test
        adapter.setOnItemClickListener(object: OnItemClickListener {
            override fun onClick(position: Int) {
                Log.i("Recipe values", newArrayList[position].toString())
                replaceFragment(view, newArrayList[position])
            }
        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun registerAdapterOnCheck(adapter: RecipeItemAdapter) {
        adapter.setOnItemCheckListener(object: OnItemCheckListener {
            override fun onChecked(position: Int, isChecked: Boolean) {
                val recipe = newArrayList[position]

                GlobalScope.launch(Dispatchers.IO) {
                    recipe.isFavorite = isChecked

                    if (recipe.isFavorite) {
                        addRecipeToFavorites(recipe)
                        Log.i("Recipe values", newArrayList[position].toString())
                    } else if (!recipe.isFavorite) {
                        removeRecipeFromFavorites(recipe)
                        Log.i("Recipe values", newArrayList[position].toString())
                    }
                }
            }
        })
    }

    private fun addRecipeToFavorites(recipe: RecipeItem) {
        removeRecipeFromFavorites(recipe)
        recipeDao.insert(recipe)
    }

    private fun removeRecipeFromFavorites(recipe: RecipeItem) {
        //val existingRecipe = recipeDao.select(recipe.id)
        val existingRecipe = recipeDao.select(true)
        if (existingRecipe != null) {
            recipeDao.delete(recipe)
        } else {
            Log.i("Recipe not found", "The recipe with id ${recipe.id} was not found in the database")
        }
    }

    private fun replaceFragment(view: View, recipe: RecipeItem) {
        val frame: FrameLayout = view.findViewById(R.id.recipe_browser_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, RecipeFragment(recipe))
            .commit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchEngine(view: View) {
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query!!.isNotEmpty()) {
                    tempArrayList.clear()
                    apiEndpointQuery = query.lowercase()

                    GlobalScope.launch {
                        try {
                            newArrayList = MainRepository().downloadAssetList(apiEndpointQuery)
                            tempArrayList.addAll(newArrayList)

                            for (i in tempArrayList.indices) {
                                val item = tempArrayList[i]
                                item.isFavorite = false
                                item.id = i + 1
                            }

                            withContext(Dispatchers.Main) {
                                val newAdapter = RecipeItemAdapter(this, tempArrayList, recipeDao)
                                recyclerView.adapter = newAdapter

                                registerAdapterOnClick(view, newAdapter)
                                registerAdapterOnCheck(newAdapter)
                            }

                            GlobalScope.launch(Dispatchers.IO) {
                                val savedSearches = recipeDao.getAllRecipes()

                                for (i in savedSearches.indices) {
                                    val item = savedSearches[i]
                                    item.id = i + 1
                                }

                                val lastIndex = savedSearches.lastIndex
                                val searchResult = SearchResult(lastIndex+1, query, tempArrayList as List<RecipeItem>)
                                Log.i("this search result", searchResult.toString())
                                recipeDao.insertSearchResult(searchResult)
                            }

                        } catch (e: FileNotFoundException) {
                            Log.i("catch from textChange", e.message.toString())
                        }
                    }

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener { false }

    }

}