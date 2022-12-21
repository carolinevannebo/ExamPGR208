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
import com.example.exampgr208.MainActivity
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*
import java.io.FileNotFoundException

class RecipeBrowserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var newArrayList : ArrayList<RecipeItem>
    lateinit var tempArrayList : ArrayList<RecipeItem>
    private var apiEndpointQuery = "all"
    lateinit var database : RecipeDatabase
    lateinit var recipeDao : RecipeDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_browser_fragment, container, false)

        val application = requireNotNull(this.activity).application // test
        database = DatabaseSingleton.getInstance(application)
        recipeDao = database.recipeDao()

        recyclerView = view.findViewById(R.id.recyclerview_main)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) //trenger jeg denne?

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

                adapter.setOnItemClickListener(object: RecipeItemAdapter.OnItemClickListener {
                    override fun onClick(position: Int) {
                        Log.i("Recipe values", newArrayList[position].toString())
                        replaceFragment(view, newArrayList[position])
                    }
                })

                adapter.setOnItemCheckListener(object: RecipeItemAdapter.OnItemCheckListener {
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
        }

        searchEngine(view)
        return view
    }

    private fun addRecipeToFavorites(recipe: RecipeItem) {
        removeRecipeFromFavorites(recipe) // ny
        recipeDao.insert(recipe)
        Log.i("favorite added", recipe.toString())
    }

    private fun removeRecipeFromFavorites(recipe: RecipeItem) {
        val existingRecipe = recipeDao.select(recipe.id)
        if (existingRecipe != null) {
            recipeDao.delete(recipe)
            Log.i("favorite removed", recipe.toString())
        } else {
            Log.i("Recipe not found", "The recipe with uri ${recipe.id} was not found in the database")
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
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        searchView?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                apiEndpointQuery = newText!!.lowercase()
                if (apiEndpointQuery.isNotEmpty()) {
                    GlobalScope.launch {
                        try {
                            newArrayList = MainRepository().downloadAssetList(apiEndpointQuery)
                        } catch (e: FileNotFoundException) {
                            Log.i("catch from textchange", e.message.toString())
                        }
                    }
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }

}