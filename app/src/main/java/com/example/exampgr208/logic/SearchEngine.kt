package com.example.exampgr208.logic

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*

class SearchEngine(var inputList: ArrayList<RecipeItem>) {

    @OptIn(DelicateCoroutinesApi::class)
    fun onLoad(inputAdapter: RecipeItemAdapter, inputView: EditText){

        var apiEndpointQuery: String

        GlobalScope.launch {

            val textWatcher = object: TextWatcher {
                @Override
                override fun afterTextChanged(s: Editable){
                    val input = s.toString()
                    apiEndpointQuery = if (input == "") {
                        "all"
                    } else {
                        input
                    }
                    Log.i("Check query", apiEndpointQuery)
                    GlobalScope.launch {
                        inputList = inputAdapter.getRecipeList()
                        inputList = MainRepository().downloadAssetList(apiEndpointQuery)
                        ////////// alert adapter that dataset has changed...?
                        Log.i("checkNewList", inputList.toString())
                    }
                }
                @Override
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){
                    apiEndpointQuery = "all"
                }
                @Override
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){}
            }
            inputView.addTextChangedListener(textWatcher)
        }

    }

}