package com.example.exampgr208.data

import android.support.v4.os.IResultReceiver
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exampgr208.logic.interfaces.IRecipe
import com.squareup.moshi.Json

@Entity
data class RecipeItem(
    @PrimaryKey @Json(name = "uri") override val uri: String? = null, // skal label være PK?
    @ColumnInfo(name = "label") override val label: String? = null, //mulig val må endres til var, null er vel bare default?
    @ColumnInfo(name = "image") override val image: String? = null,
    @ColumnInfo(name = "source") override val source: String? = null,
    @ColumnInfo(name = "url") override val url: String? = null,
    @ColumnInfo(name = "ingredientLines") override val ingredientLines: MutableList<String>? = null,
    @ColumnInfo(name = "mealType") override val mealType: String? = null
) : IRecipe { //du har lagt til override på alle val for at den skal kunne arve fra interface, og json etter PK
    override fun toString(): String {
        return "$label"
    }

    fun ingredientLinesToString() : String {
        var string = ""
        if (ingredientLines != null) {
            for (line in this.ingredientLines) {
                string += "$line#"
            }
        }
        return string
    }

}
