package com.example.exampgr208.data.database

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.exampgr208.MainActivity
import kotlin.coroutines.CoroutineContext

object DatabaseSingleton {

    private var INSTANCE: RecipeDatabase? = null

    fun getInstance(context: Context): RecipeDatabase { //løste seg med context: MainActivity
        Log.i("context in singleton", context.toString())
        if (INSTANCE == null) {
            synchronized(RecipeDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(context.applicationContext, //NullPointerException: Attempt to invoke virtual method 'android.content.Context android.content.Context.getApplicationContext()' on a null object reference
            RecipeDatabase::class.java, "recipe_database")
            .build()
}