package com.example.exampgr208.data.database

import androidx.room.Embedded
import androidx.room.Relation
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList

data class ListWithRecipeLists(
    @Embedded val Recipes: RecipeList,
    @Relation(
        parentColumn = "Id",
        entityColumn = "Uri"
    )
    val RecipeItems: List<RecipeItem>
)
