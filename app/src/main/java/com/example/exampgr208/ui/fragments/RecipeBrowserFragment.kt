package com.example.exampgr208.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*
import java.io.FileNotFoundException

class RecipeBrowserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var newArrayList : ArrayList<RecipeItem>
    lateinit var tempArrayList : ArrayList<RecipeItem>
    private lateinit var initialArrayList : ArrayList<RecipeItem>
    private var apiEndpointQuery = "all"

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.recipe_browser_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyclerview_main)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) //trenger jeg denne?

        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        initialArrayList = arrayListOf()

        GlobalScope.launch {
            initialArrayList = MainRepository().downloadAssetList(apiEndpointQuery)
            initialArrayList.forEach {
                newArrayList.add(it)
            }
            tempArrayList.addAll(newArrayList)
            withContext(Dispatchers.Main){
                val adapter = RecipeItemAdapter(this, tempArrayList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object: RecipeItemAdapter.OnItemClickListener {

                    override fun onClick(position: Int) {
                        val intent = Intent(context, RecipeFragment::class.java)
                        intent.putExtra("label", newArrayList[position].label)
                        intent.putExtra("image", newArrayList[position].image)

                        replaceFragment(view, intent)
                        Log.i("finished f-switching?", "done")
                    }
                })
            }
        }

        searchEngine(view)
        return view
    }

    private fun replaceFragment(view: View, intent: Intent) {
        //val parent: RelativeLayout = view.findViewById(R.id.recipe_browser_container)
        val frame: FrameLayout = view.findViewById(R.id.recipe_browser_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, RecipeFragment(intent))
            //???.hide(RecipeBrowserFragment())
            //.replace(this.id, RecipeFragment(intent))
            //.add(parent.id, RecipeFragment(intent))
            .commit()

        /*val backBtn = RecipeFragment(intent).requireView().findViewById<ImageButton>(R.id.back_btn)
        if (backBtn.isPressed) {
            parent.removeAllViews()
            childFragmentManager.beginTransaction()
                .addToBackStack(null)
                .setReorderingAllowed(true)

        }*/
    }

    fun navigateBackToParentFragment(view: View) {
        val parent: RelativeLayout = view.findViewById(R.id.recipe_browser_container)
        parent.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .add(parent.id, RecipeBrowserFragment())
            .commit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchEngine(view: View) {
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        searchView?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                apiEndpointQuery = newText!!.lowercase()
                if (apiEndpointQuery.isNotEmpty()) {
                    GlobalScope.launch {
                        try {
                            newArrayList = MainRepository().downloadAssetList(apiEndpointQuery)
                        } catch (e: FileNotFoundException) {
                            Log.i("catch from textchange", e.message.toString())
                        }
                    }
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }

}