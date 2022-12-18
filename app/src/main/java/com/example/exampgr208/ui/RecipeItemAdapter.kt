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

    //private lateinit var onItemClickListener: AdapterView.OnItemClickListener //new
    private var onItemClickListener: OnItemClickListener? = null
    var recipeList = RecipeList().recipeList

    //lateinit var childFragmentManager: FragmentManager // ny
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        //childFragmentManager = RecipeBrowserFragment().childFragmentManager //ny
        //RecipeBrowserFragment().isAdded
        return ViewHolder(view)
    }

    @JvmName("setOnItemClickListener1")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    /*fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener) {
        this.onItemClickListener = object : OnItemClickListener, AdapterView.OnItemClickListener {
            override fun onClick(viewHolder: ViewHolder) {

            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }
        }
    }*/

    interface OnItemClickListener {
        fun onClick(recipeItem: RecipeItem)
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

        //nytt
        holder.viewLabel.setOnClickListener {
            onItemClickListener?.onClick(recipe)
        }
        /*
            println("title click worked")
            replaceChildFragment(RecipeBrowserFragment().childFragmentManager)
            println("childFragmentManager transaction finished")
        }*/

    }
    /*private fun replaceChildFragment(childFragmentManager: FragmentManager) {
        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(R.id.recipe_browser_container, RecipeFragment())
            .commit()
    }*/

    override fun getItemCount(): Int {
        return recipeList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage: ImageView
        val viewLabel: TextView
        val viewYield: TextView
        val viewDiet: TextView
        val viewMealType: TextView
        val viewCalories: TextView
        //val contentContainer: LinearLayout //ny
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewDiet = itemView.findViewById(R.id.viewDiet)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
            //contentContainer = itemView.findViewById(R.id.clickable_layout) //ny
        }
    }

    init {
        this.recipeList = recipeList
    }
}