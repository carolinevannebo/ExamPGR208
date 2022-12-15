package com.example.exampgr208.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.MainActivity
import com.example.exampgr208.R
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*

class SearchFragment : Fragment(R.layout.search_fragment) {
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
        val view = inflater.inflate(R.layout.search_fragment, container, false)

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
                recyclerView.adapter = RecipeItemAdapter(this, tempArrayList)
            }
        }
        searchEngine()

        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchEngine() {
        val searchView = view?.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        searchView?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                apiEndpointQuery = newText!!.lowercase()
                if (apiEndpointQuery.isNotEmpty()) {
                    GlobalScope.launch { newArrayList = MainRepository().downloadAssetList(apiEndpointQuery) }
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