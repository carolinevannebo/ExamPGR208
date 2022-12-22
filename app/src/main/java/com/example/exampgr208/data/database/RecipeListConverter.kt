package com.example.exampgr208.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.exampgr208.logic.models.RecipeItem

class RecipeListConverter {
    @TypeConverter
    fun fromList(list: List<*>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<*> {
        val type = object : TypeToken<List<*>>() {}.type
        return Gson().fromJson(json, type)
    }
}