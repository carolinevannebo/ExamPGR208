package com.example.exampgr208.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class RecipeItemAdapter(private val context: CoroutineScope, recipeList: ArrayList<RecipeItem>) : // var private val context: Context
    RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    private var recipeList = RecipeList().recipeList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe : RecipeItem = recipeList!![position]

        val yieldString = "Yield: " + recipe.yield.toString()
        val mealTypeString = "Meal Type: " + recipe.mealType
        val caloriesString = "Calories: " + recipe.calories.toString()

        holder.viewImage.setImageBitmap(recipe.image)
        holder.viewLabel.text = recipe.label
        holder.viewYield.text = yieldString
        holder.viewMealType.text = mealTypeString
        holder.viewCalories.text = caloriesString
    }

    override fun getItemCount(): Int {
        return recipeList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage: ImageView
        val viewLabel: TextView
        val viewYield: TextView
        val viewMealType: TextView
        val viewCalories: TextView
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
        }
    }

    init { this.recipeList = recipeList }
}