package com.example.exampgr208.ui

import android.app.Application
import android.app.appsearch.GlobalSearchSession
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.MainActivity
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.ui.fragments.RecipeBrowserFragment
import kotlinx.coroutines.*

class RecipeItemAdapter(
    private val coroutineScope: CoroutineScope,
    private var recipeList: ArrayList<RecipeItem>,
    //private val context: Context = MainActivity().applicationContext
) : RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemCheckListener: OnItemCheckListener? = null

    private val context: Context = MainActivity().applicationContext

    //lateinit var database : RecipeDatabase
    var database : RecipeDatabase = DatabaseSingleton.getInstance(context)
    lateinit var recipeDao : RecipeDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        return ViewHolder(view)
    }

    @JvmName("setOnItemClickListener1")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
        Log.i("fun clickListener", this.onItemClickListener.toString())
    }

    fun setOnItemCheckListener(onItemCheckListener: OnItemCheckListener?) {
        this.onItemCheckListener = onItemCheckListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    interface OnItemCheckListener {
        fun onChecked(position: Int, isChecked: Boolean)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        for (i in recipeList.indices) {
            val currentRecipe = recipeList[i]
            currentRecipe.id = i + 1
            Log.i("id", currentRecipe.id.toString())
        }

        val recipe : RecipeItem = recipeList[position]

        val yieldString = "Yield: " + recipe.yield.toString()
        var dietString = "Diet: Not specified"
        if (recipe.dietLabels != null) {
            dietString = "Diet: " + recipe.dietLabels.toString()
        }
        val mealTypeString = "Meal Type: " + recipe.mealType
        val caloriesString = "Calories: " + recipe.calories.toString()

        holder.viewImage.setImageBitmap(recipe.convertImage())
        holder.viewLabel.text = recipe.label
        holder.viewYield.text = yieldString
        holder.viewDiet.text = dietString
        holder.viewMealType.text = mealTypeString
        holder.viewCalories.text = caloriesString

        holder.viewLabel.setOnClickListener {
            onItemClickListener?.onClick(position)
        }

        holder.viewCheckFavBtn.setOnCheckedChangeListener { view, _ ->
            GlobalScope.launch(Dispatchers.IO) {

                database = context.let { DatabaseSingleton.getInstance(it) }
                recipeDao = database.recipeDao()

                val existingRecipe = recipeDao.select(recipe.uri)
                holder.viewCheckFavBtn.isChecked = existingRecipe != null

                if (view is CheckBox) {
                    if (view.isChecked) {
                        onItemCheckListener?.onChecked(position, isChecked = true)
                        addRecipeToFavorites(recipe)    //lagre til db
                    } else {
                        onItemCheckListener?.onChecked(position, isChecked = false)
                        removeRecipeFromFavorites(recipe) //lagre til db
                    }
                }

            }
        }

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

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage: ImageView
        val viewLabel: TextView
        val viewYield: TextView
        val viewDiet: TextView
        val viewMealType: TextView
        val viewCalories: TextView
        //val contentContainer: LinearLayout //ny
        var viewCheckFavBtn: CheckBox
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewDiet = itemView.findViewById(R.id.viewDiet)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
            //contentContainer = itemView.findViewById(R.id.clickable_layout) //ny
            viewCheckFavBtn = itemView.findViewById(R.id.checkFavoriteBtn)
        }
    }

    init {
        this.recipeList = recipeList
    }
}
