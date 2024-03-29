package com.example.exampgr208.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.logic.models.RecipeItem
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.logic.interfaces.OnItemCheckListener
import com.example.exampgr208.logic.interfaces.OnItemClickListener
import kotlinx.coroutines.*

class RecipeItemAdapter(
    private val coroutineScope: CoroutineScope,
    private var recipeList: ArrayList<RecipeItem>,
    private val recipeDao: RecipeDao
) : RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemCheckListener: OnItemCheckListener? = null

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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

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

        holder.contentContainer.setOnClickListener {
            onItemClickListener?.onClick(position)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val existingRecipe = recipeDao.select(recipe.id)
            withContext(Dispatchers.Main) {
                holder.viewCheckFavBtn.isChecked = existingRecipe != null
            }
        }

        holder.viewCheckFavBtn.setOnCheckedChangeListener { view, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {

                    if (view is CheckBox) {
                        if (view.isChecked) {
                            onItemCheckListener?.onChecked(position, isChecked = true)
                        } else {
                            onItemCheckListener?.onChecked(position, isChecked = false)
                        }
                    }
                }

            }
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
        val contentContainer: LinearLayout
        var viewCheckFavBtn: CheckBox
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewDiet = itemView.findViewById(R.id.viewDiet)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
            contentContainer = itemView.findViewById(R.id.clickable_layout)
            viewCheckFavBtn = itemView.findViewById(R.id.checkFavoriteBtn)
        }
    }

    init {
        this.recipeList = recipeList
    }
}
