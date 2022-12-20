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
    private lateinit var initialArrayList : ArrayList<RecipeItem>
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

        recyclerView = view.findViewById(R.id.recyclerview_main)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) //trenger jeg denne?

        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        initialArrayList = arrayListOf()

        GlobalScope.launch {
            //initialArrayList = MainRepository().downloadAssetList(apiEndpointQuery)
            newArrayList = MainRepository().downloadAssetList(apiEndpointQuery)

            /*initialArrayList.forEach {
                newArrayList.add(it)
            }
            newArrayList.addAll(initialArrayList)*/
            tempArrayList.addAll(newArrayList)

            withContext(Dispatchers.Main){
                database = context?.let { DatabaseSingleton.getInstance(it) }!!
                recipeDao = database.recipeDao()

                val adapter = RecipeItemAdapter(this, tempArrayList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object: RecipeItemAdapter.OnItemClickListener {
                    override fun onClick(position: Int) {
                        /*val intent = Intent(context, RecipeFragment::class.java)

                        intent.putExtra("uri", newArrayList[position].uri)
                        intent.putExtra("label", newArrayList[position].label)
                        intent.putExtra("image", newArrayList[position].image)
                        intent.putExtra("isFavorite", newArrayList[position].isFavorite) //midlertidig løsning for å bevare check on favorite
                        //intent.putExtra("test", newArrayList[position] as Parcelable)

                        val selectedRecipe = newArrayList[position]
                        val args = Bundle()
                        args.putParcelableArrayList("recipes", newArrayList)
                        args.putParcelable("selectedRecipe", selectedRecipe)
                        val recipeFragment = RecipeFragment()
                        recipeFragment.arguments = args
                        replaceFragment(view)*/

                        //replaceFragment(view, intent)
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
                            } else if (!recipe.isFavorite) {
                                removeRecipeFromFavorites(recipe)
                            }
                        }
                        //Log.i("favorite?", newArrayList[position].isFavorite.toString())
                        //Log.i("isChecked?", isChecked.toString())
                    }
                })
            }
        }

        searchEngine(view)
        return view
    }

    private fun addRecipeToFavorites(recipe: RecipeItem) {
        recipeDao.insert(recipe)
        Log.i("favorite added", recipe.toString())
    }

    private fun removeRecipeFromFavorites(recipe: RecipeItem) {
        val existingRecipe = recipeDao.select(recipe.uri)
        if (existingRecipe != null) {
            recipeDao.delete(recipe)
            Log.i("favorite removed", recipe.toString())
        } else {
            Log.i("Recipe not found", "The recipe with uri ${recipe.uri} was not found in the database")
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
    /*private fun replaceFragment(view: View, intent: Intent) {
        val frame: FrameLayout = view.findViewById(R.id.recipe_browser_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, RecipeFragment(intent))
            .commit()
    }*/

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