package com.example.exampgr208

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.exampgr208.data.database.DatabaseSingleton
import com.example.exampgr208.databinding.ActivityMainBinding
import com.example.exampgr208.ui.fragments.FavoriteFragment
import com.example.exampgr208.ui.fragments.RecipeBrowserFragment
import com.example.exampgr208.ui.fragments.SearchHistoryFragment
import com.example.exampgr208.ui.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    //lateinit var context: Context
    //var context: Context = this.applicationContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //context = applicationContext
        fragmentManager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        //context = baseContext!!
        //super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(RecipeBrowserFragment())

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
    }

    private fun replaceFragment(fragment : Fragment) {
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}
