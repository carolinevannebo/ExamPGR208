package com.example.exampgr208.logic.interfaces

import android.graphics.Bitmap

interface IRecipe {
    val uri: String?
    val label: String?
    val image: ByteArray?
    val source: String?
    val url: String?
    val yield: Int?
    val dietLabels: String?
    val healthLabels: String?
    val cautions: String?
    val ingredientLines: String?
    val mealType: String?
    val calories: Int?
    val isFavorite: Boolean
    fun convertImage(): Bitmap?
}