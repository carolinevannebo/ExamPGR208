package com.example.exampgr208.logic.interfaces

import android.graphics.Bitmap

interface IRecipe {
    val uri: String?
    val label: String?
    val image: Bitmap?
    val source: String?
    val url: String?
    val yield: Int?
    //val dietLabels: MutableList<String>?
    //val healthLabels: MutableList<String>?
    //val cautions: MutableList<String>?
    //val ingredientLines: MutableList<String>?
    val mealType: String?
    val calories: Int?
}