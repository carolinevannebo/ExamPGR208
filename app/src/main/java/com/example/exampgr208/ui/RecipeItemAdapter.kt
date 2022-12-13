package com.example.exampgr208.ui

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

class RecipeItemAdapter(private val context: CoroutineScope, recipeList: ArrayList<RecipeItem>) : // var private val context: Context
    RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    var recipeList = RecipeList().recipeList
    //var recipeList = arrayListOf<RecipeItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        //test
        //ViewHolder(view).setIsRecyclable(true)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe : RecipeItem = recipeList!![position]

        val yieldString = "Yield: " + recipe.yield.toString()
        var dietString = "Diet: Not specified"
        if (recipe.dietLabels != null) {
            dietString = "Diet: " + recipe.dietLabels.toString()
        }
        val mealTypeString = "Meal Type: " + recipe.mealType
        val caloriesString = "Calories: " + recipe.calories.toString()

        holder.viewImage.setImageBitmap(recipe.image)
        holder.viewLabel.text = recipe.label
        holder.viewYield.text = yieldString
        holder.viewDiet.text = dietString
        holder.viewMealType.text = mealTypeString
        holder.viewCalories.text = caloriesString
    }

    override fun getItemCount(): Int {
        return recipeList!!.size
    }

    /*fun getRecipeList() : ArrayList<RecipeItem> {
        return recipeList as ArrayList<RecipeItem>
    }*/

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage: ImageView
        val viewLabel: TextView
        val viewYield: TextView
        val viewDiet: TextView
        val viewMealType: TextView
        val viewCalories: TextView
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewDiet = itemView.findViewById(R.id.viewDiet)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
        }
    }

    init {
        this.recipeList = recipeList
        //this.notifyDataSetChanged()
    }
}