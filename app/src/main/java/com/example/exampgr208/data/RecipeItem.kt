package com.example.exampgr208.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exampgr208.logic.interfaces.IRecipe
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "recipes")
data class RecipeItem(
    @PrimaryKey(autoGenerate = true) var id: Int? = 0,
    @Json(name = "uri") override var uri: String? = null,
    @ColumnInfo(name = "label") override var label: String? = null,
    @ColumnInfo(name = "image") override var image: ByteArray? = null,
    @ColumnInfo(name = "source") override var source: String? = null,
    @ColumnInfo(name = "url") override var url: String? = null,
    @ColumnInfo(name = "yield") override var yield: Int? = null,
    @ColumnInfo(name = "diet_labels") override var dietLabels: String? = null,
    @ColumnInfo(name = "health_labels") override var healthLabels: String? = null,
    @ColumnInfo(name = "cautions") override var cautions: String? = null,
    @ColumnInfo(name = "ingredients") override var ingredientLines: String? = null,
    @ColumnInfo(name = "meal_type") override var mealType: String? = null,
    @ColumnInfo(name = "calories") override var calories: Int? = null,
    @ColumnInfo(name = "is_favorite") override var isFavorite: Boolean = false
) : IRecipe, Serializable, Parcelable {

    //@JvmName("getImage1")
    override fun convertImage(): Bitmap? {
        return image?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }

    override fun toString(): String {
        return "$label"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeItem

        if (id != other.id) return false
        if (uri != other.uri) return false
        if (label != other.label) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (source != other.source) return false
        if (url != other.url) return false
        if (yield != other.yield) return false
        if (dietLabels != other.dietLabels) return false
        if (healthLabels != other.healthLabels) return false
        if (cautions != other.cautions) return false
        if (ingredientLines != other.ingredientLines) return false
        if (mealType != other.mealType) return false
        if (calories != other.calories) return false
        if (isFavorite != other.isFavorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (uri?.hashCode() ?: 0)
        result = 31 * result + (label?.hashCode() ?: 0)
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (yield ?: 0)
        result = 31 * result + (dietLabels?.hashCode() ?: 0)
        result = 31 * result + (healthLabels?.hashCode() ?: 0)
        result = 31 * result + (cautions?.hashCode() ?: 0)
        result = 31 * result + (ingredientLines?.hashCode() ?: 0)
        result = 31 * result + (mealType?.hashCode() ?: 0)
        result = 31 * result + (calories ?: 0)
        result = 31 * result + isFavorite.hashCode()
        return result
    }


}
