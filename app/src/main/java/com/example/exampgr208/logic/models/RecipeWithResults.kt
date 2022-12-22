package com.example.exampgr208.logic.models

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithResults(
    @Embedded val searchResult: SearchResult,
    @Relation(parentColumn = "result", entityColumn = "id")
    val result: List<RecipeItem>
)