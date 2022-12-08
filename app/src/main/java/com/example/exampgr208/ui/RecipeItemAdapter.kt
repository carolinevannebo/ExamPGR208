package com.example.exampgr208.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList
import kotlin.coroutines.CoroutineContext

class RecipeItemAdapter(private val context: Context, recipeList: ArrayList<RecipeItem>) : // var private val context: CoroutineContext
    RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    private var recipeList = RecipeList().recipeList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe : RecipeItem = recipeList!![position]
        holder.viewLabel.text = recipe.label
        holder.viewMealType.text = recipe.mealType
    }

    override fun getItemCount(): Int {
        return recipeList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewLabel: TextView
        val viewMealType: TextView
        init {
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewMealType = itemView.findViewById(R.id.viewMealType)

        }
    }

    init { this.recipeList = recipeList }
}