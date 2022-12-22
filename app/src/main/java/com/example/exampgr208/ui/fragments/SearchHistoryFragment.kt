package com.example.exampgr208.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.logic.models.SearchResult
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.data.database.RecipeDao
import com.example.exampgr208.data.database.RecipeDatabase
import com.example.exampgr208.logic.interfaces.OnItemClickListener
import com.example.exampgr208.ui.adapters.SearchQueryItemAdapter
import kotlinx.coroutines.*

class SearchHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var searchHistoryArrayList : ArrayList<SearchResult>
    lateinit var searchQueriesArrayList : ArrayList<String>
    lateinit var database : RecipeDatabase
    lateinit var recipeDao : RecipeDao

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_history_fragment, container, false)

        val application = requireNotNull(this.activity).application
        database = DatabaseSingleton.getInstance(application)
        recipeDao = database.recipeDao()

        recyclerView = view.findViewById(R.id.recyclerview_history)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        searchHistoryArrayList = arrayListOf()
        searchQueriesArrayList = arrayListOf()

        GlobalScope.launch(Dispatchers.IO) {
            //searchHistoryArrayList = recipeDao.getAllSearchResults() as ArrayList<SearchResult> // Row too big to fit into CursorWindow
            searchQueriesArrayList = recipeDao.getAllSearchQueries() as ArrayList<String>

            for (i in searchQueriesArrayList.indices) {
                Log.i("search history list", searchQueriesArrayList[i])
            }

            withContext(Dispatchers.Main) {
                val adapter = SearchQueryItemAdapter(searchQueriesArrayList)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener(object: OnItemClickListener {
                    override fun onClick(position: Int) {
                        replaceFragment(view, searchQueriesArrayList[position] )
                    }
                })
            }

        }

        return view
    }

    private fun replaceFragment(view: View, query: String) {
        val frame: FrameLayout = view.findViewById(R.id.search_history_layout)
        frame.removeAllViews()

        childFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(frame.id, SearchHistoryResultListFragment(query))
            .commit()
    }

}
