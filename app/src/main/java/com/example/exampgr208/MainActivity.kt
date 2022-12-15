package com.example.exampgr208

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
//import com.example.exampgr208.data.database.AppDatabase
//import com.example.exampgr208.data.database.RecipeItemDao
import com.example.exampgr208.databinding.ActivityMainBinding
import com.example.exampgr208.ui.fragments.FavoriteFragment
import com.example.exampgr208.ui.fragments.RecipeBrowserFragment
import com.example.exampgr208.ui.fragments.SearchHistoryFragment
import com.example.exampgr208.ui.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    /*private lateinit var recyclerView: RecyclerView
    //private lateinit var recyclerViewError: RecyclerView
    lateinit var newArrayList : ArrayList<RecipeItem>
    lateinit var tempArrayList : ArrayList<RecipeItem>
    private lateinit var initialArrayList : ArrayList<RecipeItem>
    private var apiEndpointQuery = "all"*/

    //@OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)      //(R.layout.activity_main) //(binding.root)
        //replaceFragment(RecipeBrowserFragment())
        fragmentManager.beginTransaction()
            .replace(
                R.id.frame_layout,
                RecipeBrowserFragment()
            ).commit()

        binding.navBar.selectedItemId = R.id.nav_home
        binding.navBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_fav -> replaceFragment(FavoriteFragment())
                R.id.nav_search_history -> replaceFragment(SearchHistoryFragment())
                R.id.nav_home -> replaceFragment(RecipeBrowserFragment())
                R.id.nav_settings -> replaceFragment(SettingsFragment())
            }
            true
        }
        //binding.navBar[R.id.nav_home].isSelected = true

        /*val favoriteBtn: ImageButton = findViewById(R.id.nav_fav)
        favoriteBtn.setOnClickListener {
            replaceFragment(FavoriteFragment())
        }

        val homeBtn: ImageButton = findViewById(R.id.nav_home)
        homeBtn.setOnClickListener {
            replaceFragment(RecipeBrowserFragment())
        }*/

        /*setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview_main)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
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
                /*if (recyclerView.visibility == RecyclerView.GONE) {
                    recyclerView.visibility = RecyclerView.VISIBLE
                }*/
                //val layoutInflater = LayoutInflater.from(this@MainActivity).inflate(R.layout.rec)
            }
        }
        /*GlobalScope.launch(Dispatchers.Main){

            val recipesRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_main)
            val apiEndpointQuery = "all"
            val recipeList: ArrayList<RecipeItem> = MainRepository().downloadAssetList(apiEndpointQuery)
            val recipeItemAdapter = RecipeItemAdapter(this, recipeList)
            val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            recipesRecyclerView.layoutManager = linearLayoutManager
            recipesRecyclerView.adapter = recipeItemAdapter
        }*/
        searchEngine()*/
    }

    private fun replaceFragment(fragment : Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    /*@OptIn(DelicateCoroutinesApi::class)
    fun searchEngine() {
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        /*recyclerViewError = findViewById(R.id.error_layout)
        recyclerViewError.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)*/
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //recyclerView.adapter!!.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                apiEndpointQuery = newText!!.lowercase()
                if (apiEndpointQuery.isNotEmpty()) {
                    GlobalScope.launch { newArrayList = MainRepository().downloadAssetList(apiEndpointQuery) }
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                    /*if (!newArrayList.isNullOrEmpty()) {
                        /*recyclerViewError.visibility = RecyclerView.GONE
                        recyclerView.visibility = RecyclerView.VISIBLE*/
                        tempArrayList.addAll(newArrayList)
                        recyclerView.adapter!!.notifyDataSetChanged()

                    } else if (newArrayList.isNullOrEmpty()) {
                        /*recyclerView.visibility = RecyclerView.GONE
                        recyclerViewError.visibility = RecyclerView.VISIBLE*/
                    }*/
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }*/

}

//      --- Spagetti som var i onCreate ---
/*
//test
/*val searchBar = findViewById<EditText>(R.id.search_bar)
searchBar.setOnClickListener{
    val listFromSearch = SearchEngine(recipeList).onLoad(searchBar) //selv du er i mot meg!!!!!!!!"!"!
    recipeItemAdapter.recipeList = listFromSearch
    recipeItemAdapter.notifyDataSetChanged()
    Log.i("search list fucker", listFromSearch.toString())
    Log.i("fuck this list", recipeItemAdapter.recipeList.toString())
    //recipeItemAdapter = RecipeItemAdapter(this, listFromSearch)
}
recipeItemAdapter.notifyDataSetChanged()*/
//val listFromSearch = SearchEngine(recipeList).onLoad(findViewById<View>(R.id.search_bar) as EditText)
//val recipeItemAdapter = RecipeItemAdapter(this, listFromSearch)
//

//test
//val listFromSearch = SearchEngine(recipeList).onLoad(/*recipeItemAdapter,*/ findViewById<View>(R.id.search_bar) as EditText)
//recipeItemAdapter = RecipeItemAdapter(this, listFromSearch)
/*if (listFromSearch.isNotEmpty()) {
    //recipeItemAdapter.recipeList!!.clear()
    recipeItemAdapter.recipeList = recipeList
    recipeItemAdapter.notifyDataSetChanged()
    Log.i("list in main adapter", recipeItemAdapter.recipeList.toString())
}*/
//

//SearchEngine(recipeList).onLoad(recipeItemAdapter, findViewById<View>(R.id.search_bar) as EditText)
// tester søkefelt
/*recipesRecyclerView.hasPendingAdapterUpdates()
val listAfterSearch = SearchEngine(recipeList).onLoad(recipeItemAdapter, findViewById<View>(R.id.search_bar) as EditText)
recipesRecyclerView.adapter = RecipeItemAdapter(this, listAfterSearch)*/

//Log.i("pendingUpdates?", recipesRecyclerView.hasPendingAdapterUpdates().toString())
/*val listAfterSearch = SearchEngine(recipeList).onLoad(recipeItemAdapter, findViewById<View>(R.id.search_bar) as EditText)
recipeItemAdapter = RecipeItemAdapter(this, listAfterSearch)*/
//recipeItemAdapter.notifyDataSetChanged()
//recipesRecyclerView.adapter = recipeItemAdapter

//

//mer test
//recipeItemAdapter.notifyDataSetChanged()
//val dataObserver = RecyclerView.AdapterDataObserver()
//recipesRecyclerView.AdapterDataObserver
//recipesRecyclerView.hasPendingAdapterUpdates()
//fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver): Unit {}
*/