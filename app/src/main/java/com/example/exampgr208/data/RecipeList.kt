package com.example.exampgr208.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exampgr208.data.RecipeItem

@Entity
data class RecipeList(
    //@PrimaryKey val uri: String? = null,
    @PrimaryKey(autoGenerate = true) val listId: Int? = null,
    @ColumnInfo val recipeList: MutableList<RecipeItem>? = null
) {
    fun recipeListResponse(): List<RecipeItem> {
        return recipeList!!.map { it }
    }
}
