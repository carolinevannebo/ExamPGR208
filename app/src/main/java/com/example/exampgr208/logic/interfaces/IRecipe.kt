package com.example.exampgr208.logic.interfaces

interface IRecipe {
    val uri: String?
    val label: String?
    val image: String?
    val source: String?
    val url: String?
    val ingredientLines: MutableList<String>?
    val mealType: String?
}