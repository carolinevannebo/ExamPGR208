package com.example.exampgr208.ui.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeFragment(/*private var intent: Intent*/private var recipe: RecipeItem) : Fragment() {
    //lateinit var recipeItem: RecipeItem

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_fragment, container, false)

        val recipeContainer : LinearLayout = view.findViewById(R.id.recipe_container)
        recipeContainer.background = getDrawableWithRadius()

        val backBtn : ImageButton = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            replaceFragment(view)
        }

        val checkBoxView : CheckBox = view.findViewById(R.id.checkFavoriteBtn)
        val labelView : TextView = view.findViewById(R.id.viewLabel)
        val imageView : ImageView = view.findViewById(R.id.viewImage)

        val image = BitmapFactory.decodeByteArray(recipe.image, 0, recipe.image!!.size)

        checkBoxView.isChecked = recipe.isFavorite
        labelView.text = recipe.label
        imageView.setImageBitmap(image)



        /*val bundle : Bundle? = intent.extras
        val uri = bundle!!.getString("uri")
        val label = bundle.getString("label")
        val imageByteArray = bundle.getByteArray("image")
        val image = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray!!.size)
        var isFavorite = bundle.getBoolean("isFavorite")
        //val test = bundle.getParcelable("test", RecipeItem)

        checkBoxView.isChecked = isFavorite
        labelView.text = label
        imageView.setImageBitmap(image)
        }*/

        return view
    }

    private fun replaceFragment(view: View) {
        val frame: FrameLayout = view.findViewById(R.id.recipe_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, RecipeBrowserFragment())
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDrawableWithRadius() : Drawable {
        val gradientDrawable = GradientDrawable()
        val colorIdRes = R.color.lighter_bg
        val colorInt = this.context?.let { ContextCompat.getColor(it, colorIdRes) }
        gradientDrawable.cornerRadii = floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f)
        gradientDrawable.setColor(colorInt!!)
        return gradientDrawable
    }

}