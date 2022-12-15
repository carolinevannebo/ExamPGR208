package com.example.exampgr208.data.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "RecipeHelperDB.db"
        ).build()
}