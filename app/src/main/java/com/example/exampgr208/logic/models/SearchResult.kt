package com.example.exampgr208.logic.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.exampgr208.data.database.RecipeListConverter

@Entity(tableName = "search_results"/*,
    foreignKeys = [ForeignKey(entity = RecipeItem::class,
    parentColumns = ["id"],
    childColumns = ["result"],
    onDelete = ForeignKey.CASCADE)]*/)

data class SearchResult(
    @PrimaryKey(autoGenerate = true) val Id: Int = 0,
    @ColumnInfo(name = "search_query") val query: String? = null,
    @ColumnInfo(name = "result") var searchResult: List<*>? = null
) {
    @TypeConverter
    fun getSearchResults(): String {
        return RecipeListConverter().fromList(searchResult)
    }

    @TypeConverter
    fun setSearchResults(json: String) {
        searchResult = RecipeListConverter().toList(json)
    }

    override fun toString(): String {
        return "$Id\n$query\n$searchResult"
    }
}
