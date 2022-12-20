package com.example.exampgr208.data.database

import android.content.Context
import androidx.room.Room
import kotlin.coroutines.CoroutineContext

object DatabaseSingleton {

    private var INSTANCE: RecipeDatabase? = null

    fun getInstance(context: Context): RecipeDatabase {
        if (INSTANCE == null) {
            synchronized(RecipeDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(context.applicationContext,
            RecipeDatabase::class.java, "recipe_database")
            .build()
}