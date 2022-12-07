package com.example.exampgr208.data

import android.support.v4.os.IResultReceiver
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exampgr208.logic.interfaces.IRecipe
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class RecipeItem(
    @PrimaryKey @Json(name = "uri") override var uri: String? = null, // skal label være PK?
    @ColumnInfo(name = "label") override var label: String? = null, //mulig val må endres til var, null er vel bare default?
    @ColumnInfo(name = "image") override val image: String? = null,
    @ColumnInfo(name = "source") override var source: String? = null,
    @ColumnInfo(name = "url") override var url: String? = null,
    @ColumnInfo(name = "ingredientLines") override var ingredientLines: MutableList<String>? = null,
    @ColumnInfo(name = "mealType") override var mealType: String? = null
) : IRecipe, Serializable { //du har lagt til override på alle val for at den skal kunne arve fra interface, og json etter PK
    override fun toString(): String {
        return "$label"
    }

    fun ingredientLinesToString() : String {
        var string = ""
        if (ingredientLines != null) {
            for (line in this.ingredientLines!!) {
                string += "$line#"
            }
        }
        return string
    }

}
