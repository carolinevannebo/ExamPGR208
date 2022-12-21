package com.example.exampgr208.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.databinding.ActivityMainBinding
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var favoriteRecipesArrayList : ArrayList<RecipeItem>
    lateinit var database : RecipeDatabase
    lateinit var recipeDao : RecipeDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorite_fragment, container, false)

        val application = requireNotNull(this.activity).application // test
        database = DatabaseSingleton.getInstance(application)
        recipeDao = database.recipeDao()

        recyclerView = view.findViewById(R.id.recyclerview_favorites)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        favoriteRecipesArrayList = arrayListOf()

        GlobalScope.launch(Dispatchers.IO) {
            favoriteRecipesArrayList = recipeDao.getAllRecipes() as ArrayList<RecipeItem>
            Log.i("fav recipes", favoriteRecipesArrayList.toString())

            withContext(Dispatchers.Main) {
                val adapter = RecipeItemAdapter(this, favoriteRecipesArrayList, recipeDao)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object: RecipeItemAdapter.OnItemClickListener {
                    override fun onClick(position: Int) {
                        Log.i("Recipe values", favoriteRecipesArrayList[position].toString())
                        replaceFragment(view, favoriteRecipesArrayList[position])
                    }
                })

                adapter.setOnItemCheckListener(object: RecipeItemAdapter.OnItemCheckListener {
                    override fun onChecked(position: Int, isChecked: Boolean) {
                        val recipe = favoriteRecipesArrayList[position]

                        GlobalScope.launch(Dispatchers.IO) {
                            recipe.isFavorite = isChecked

                            if (recipe.isFavorite) {
                                addRecipeToFavorites(recipe)
                                Log.i("Recipe values", favoriteRecipesArrayList[position].toString())
                            } else if (!recipe.isFavorite) {
                                removeRecipeFromFavorites(recipe)
                                Log.i("Recipe values", favoriteRecipesArrayList[position].toString())
                            }
                        }
                    }
                })
            }
        }

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
        val frame: FrameLayout = view.findViewById(R.id.favorites_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, RecipeFragment(recipe))
            .commit()
    }

}