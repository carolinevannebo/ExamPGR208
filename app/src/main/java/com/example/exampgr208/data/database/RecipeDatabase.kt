package com.example.exampgr208.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.SearchResult

@Database(entities = [SearchResult::class, RecipeItem::class], version = 2)
@TypeConverters(RecipeListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}


