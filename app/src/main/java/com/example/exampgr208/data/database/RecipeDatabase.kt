package com.example.exampgr208.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.exampgr208.logic.models.RecipeItem
import com.example.exampgr208.logic.models.RecipeWithResults
import com.example.exampgr208.logic.models.SearchResult

@Database(entities = [SearchResult::class, RecipeItem::class], version = 7)
@TypeConverters(RecipeListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}


