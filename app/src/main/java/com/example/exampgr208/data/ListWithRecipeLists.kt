package com.example.exampgr208.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList

data class ListWithRecipeLists(
    @Embedded val recipeList: RecipeList,
    @Relation(
        parentColumn = "listId",
        entityColumn = "uri"
    )
    val RecipeItems: List<RecipeItem>
)
