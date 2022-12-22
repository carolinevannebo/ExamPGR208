package com.example.exampgr208.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.exampgr208.logic.models.RecipeItem

class RecipeListConverter {
    @TypeConverter
    fun fromList(list: ArrayList<RecipeItem>?): String {
        /*for (i in list!!) {
            val item = list[i]

            if(item.id == null || item.id == 0) {
                val lastIndex = list.lastIndex
                item.id = lastIndex +1
            }

            if(item.isFavorite == null) {
                item.isFavorite = false
            }

        }*/
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(json: String): ArrayList<RecipeItem> {
        val type = object : TypeToken<ArrayList<RecipeItem>>() {}.type
        return Gson().fromJson(json, type)
    }
}