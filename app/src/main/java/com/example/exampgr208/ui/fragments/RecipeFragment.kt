package com.example.exampgr208.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeFragment(private var intent: Intent) : Fragment() {
    lateinit var recipeItem: RecipeItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_fragment, container, false)

        //val recipeContainer = view.findViewById<LinearLayout>(R.id.recipe_item_container)
        val labelView : TextView = view.findViewById(R.id.viewLabel)
        val imageView : ImageView = view.findViewById(R.id.viewImage)

        val bundle : Bundle? = intent.extras
        val label = bundle!!.getString("label")
        val imageId = bundle.getInt("image")

        labelView.text = label
        imageView.setImageResource(imageId)

        return view
    }
}