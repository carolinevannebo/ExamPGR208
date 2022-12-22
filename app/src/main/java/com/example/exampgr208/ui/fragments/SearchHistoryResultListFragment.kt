package com.example.exampgr208.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.logic.models.RecipeItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchHistoryResultListFragment(private var searchQuery: String) : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var recipeList : ArrayList<RecipeItem>
    lateinit var database : RecipeDatabase
    lateinit var recipeDao : RecipeDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_history_result_fragment, container, false)

        val application = requireNotNull(this.activity).application
        database = DatabaseSingleton.getInstance(application)
        recipeDao = database.recipeDao()

        recyclerView = view.findViewById(R.id.recyclerview_history_list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        recipeList = arrayListOf()

        val title = view.findViewById<TextView>(R.id.query_title)
        title.text = searchQuery

        GlobalScope.launch(Dispatchers.IO) {
            recipeList = recipeDao.select(searchQuery) as ArrayList<RecipeItem>
            //Log.i("list from results", recipeList.toString())
            Log.i("list from results", recipeDao.select(searchQuery).toString())
        }

        //lag en liste, bruk query til å hente lista, send den til adapter
        //legg til tilbakeknapp i denne fragment

        return view
    }
}