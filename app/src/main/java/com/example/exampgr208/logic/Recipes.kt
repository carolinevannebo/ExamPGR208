package com.example.exampgr208.logic

import com.example.exampgr208.data.database.AppDatabase
import com.example.exampgr208.data.ListWithRecipeLists
import com.example.exampgr208.data.RecipeItem

abstract class Recipes: AppDatabase() {
    val listsToCompare = mutableListOf<ListWithRecipeLists>()

    fun getRecipeLists(): List<ListWithRecipeLists> {
        return recipeListDao().getListWithRecipeLists()
    }

    fun getRecipeItems(): List<RecipeItem> {
        return recipeItemDao().getAll()
    }

    fun addRecipeList(recipeList: ListWithRecipeLists) {
        recipeListDao().insertAll(recipeList.recipeList)
        recipeItemDao().insertAll(*recipeList.RecipeItems.toTypedArray())
    }

    fun deleteRecipeList(recipeList: ListWithRecipeLists) {
        recipeListDao().delete(recipeList.recipeList)
    }

    fun getSubItems(item: ListWithRecipeLists): String {
        val inListForm = item.RecipeItems.mapIndexed{
                listId, value -> (listId+1).toString().plus(". ").plus(value.uri)
                .plus(value.label).plus(value.image).plus(value.source)
                .plus(value.url).plus(value.ingredientLinesToString())
                .plus(value.mealType)}
        return inListForm.joinToString("\n")
    }
}