package com.example.exampgr208.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.exampgr208.MainActivity
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

        val backBtn : ImageButton = view.findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            //childFragmentManager.popBackStack()
            //RecipeBrowserFragment().view?.let { it1 -> replaceChildFragment(it1) }
            replaceFragment(view)
        }

        val labelView : TextView = view.findViewById(R.id.viewLabel)
        val imageView : ImageView = view.findViewById(R.id.viewImage)

        val bundle : Bundle? = intent.extras
        val label = bundle!!.getString("label")
        val imageId = bundle.getInt("image")

        labelView.text = label
        imageView.setImageResource(imageId)

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

}