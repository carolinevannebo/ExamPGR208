package com.example.exampgr208.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.exampgr208.R
import com.example.exampgr208.logic.interfaces.OnItemClickListener

class SearchQueryItemAdapter(
    private var searchQueriesArrayList : ArrayList<String>
) : RecyclerView.Adapter<SearchQueryItemAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_history_item_list, parent, false)
        return ViewHolder(view)
    }

    @JvmName("setOnItemClickListener2")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchQueryItem: String = searchQueriesArrayList[position]

        holder.viewSearchQuery.text = searchQueryItem

        holder.viewSearchQuery.setOnClickListener {
            onItemClickListener?.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return searchQueriesArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewSearchQuery : TextView

        init {
            viewSearchQuery = itemView.findViewById(R.id.search_query)
        }
    }

}