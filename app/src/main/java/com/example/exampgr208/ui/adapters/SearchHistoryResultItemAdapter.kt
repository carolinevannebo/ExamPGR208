package com.example.exampgr208.ui.adapters

/*import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampgr208.R
import com.example.exampgr208.logic.interfaces.OnItemCheckListener
import com.example.exampgr208.logic.interfaces.OnItemClickListener
import com.example.exampgr208.logic.models.SearchResult

class SearchHistoryResultItemAdapter(
    private var searchHistoryArrayList : ArrayList<SearchResult>
) : RecyclerView.Adapter<SearchHistoryResultItemAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemCheckListener: OnItemCheckListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_list, parent, false)
        return ViewHolder(view)
    }

    @JvmName("setOnItemClickListener3")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemCheckListener(onItemCheckListener: OnItemCheckListener?) {
        this.onItemCheckListener = onItemCheckListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewImage: ImageView
        val viewLabel: TextView
        val viewYield: TextView
        val viewDiet: TextView
        val viewMealType: TextView
        val viewCalories: TextView
        val contentContainer: LinearLayout
        var viewCheckFavBtn: CheckBox
        init {
            viewImage = itemView.findViewById(R.id.viewImage)
            viewLabel = itemView.findViewById(R.id.viewLabel)
            viewYield = itemView.findViewById(R.id.viewYield)
            viewDiet = itemView.findViewById(R.id.viewDiet)
            viewMealType = itemView.findViewById(R.id.viewMealType)
            viewCalories = itemView.findViewById(R.id.viewCalories)
            contentContainer = itemView.findViewById(R.id.clickable_layout)
            viewCheckFavBtn = itemView.findViewById(R.id.checkFavoriteBtn)
        }
    }
}*/