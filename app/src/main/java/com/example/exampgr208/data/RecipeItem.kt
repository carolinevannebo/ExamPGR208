package com.example.exampgr208.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exampgr208.logic.interfaces.IRecipe
import com.squareup.moshi.Json
import org.json.JSONArray
import java.io.Serializable

@Entity(tableName = "Recipe")
data class RecipeItem(
    @PrimaryKey @Json(name = "uri") override var uri: String? = null, // skal label være PK?
    @ColumnInfo(name = "label") override var label: String? = null, //mulig val må endres til var, null er vel bare default?
    @ColumnInfo(name = "image") override var image: Bitmap? = null,
    @ColumnInfo(name = "source") override var source: String? = null,
    @ColumnInfo(name = "url") override var url: String? = null,
    @ColumnInfo(name = "yield") override var yield: Int? = null,
    @ColumnInfo(name = "dietLabels") override var dietLabels: String? = null,
    @ColumnInfo(name = "healthLabels") override var healthLabels: String? = null,
    @ColumnInfo(name = "cautions") override var cautions: String? = null,
    @ColumnInfo(name = "ingredientLines") override var ingredientLines: String? = null,
    @ColumnInfo(name = "mealType") override var mealType: String? = null,
    @ColumnInfo(name = "calories") override var calories: Int? = null,
    @ColumnInfo(name = "isFavorite") override var isFavorite: Boolean = false
) : IRecipe, Serializable { //du har lagt til override på alle val for at den skal kunne arve fra interface
    override fun toString(): String {
        return "$label"
    }

    /*fun ingredientLinesToString() : String {
        var string = ""
        if (ingredientLines != null) {
            for (line in this.ingredientLines!!) {
                string += "$line#"
            }
        }
        return string
    }*/

}
