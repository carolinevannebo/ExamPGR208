package com.example.exampgr208.logic

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import androidx.recyclerview.widget.AdapterListUpdateCallback
import com.example.exampgr208.data.RecipeItem
import com.example.exampgr208.data.RecipeList
import com.example.exampgr208.data.repository.MainRepository
import com.example.exampgr208.ui.RecipeItemAdapter
import kotlinx.coroutines.*

class SearchEngine(var inputList: ArrayList<RecipeItem>) {

    @OptIn(DelicateCoroutinesApi::class)
    fun onLoad(/*inputAdapter: RecipeItemAdapter,*/ inputView: EditText) : ArrayList<RecipeItem>{

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
                    //Log.i("Check query", apiEndpointQuery)
                    GlobalScope.launch {
                        inputList = MainRepository().downloadAssetList(apiEndpointQuery)
                        //inputAdapter.recipeList = inputList
                        //RecipeItemAdapter(this, inputList)
                        //inputAdapter.recipeList!!.clear()
                        /*inputList.forEach {
                            inputAdapter.recipeList!!.removeAt(0)
                            inputAdapter.recipeList!!.add(it)
                        }
                        inputAdapter.notifyDataSetChanged()*/
                        //inputAdapter.recipeList = inputList
                        //inputAdapter.notifyDataSetChanged()
                        //AdapterListUpdateCallback(inputAdapter)
                        //inputAdapter.notifyItemInserted(inputList.size)
                        //RecipeItemAdapter(this, inputList) //please
                        ////////// alert adapter that dataset has changed...?
                        Log.i("checkNewList", inputList.toString())
                        //Log.i("check list in adapter", inputAdapter.recipeList.toString())
                    }
                }
                @Override
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){
                    apiEndpointQuery = "all"
                    GlobalScope.launch {
                        inputList = MainRepository().downloadAssetList(apiEndpointQuery)
                    }
                }
                @Override
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){}
            }
            inputView.addTextChangedListener(textWatcher)
            //inputAdapter.notifyDataSetChanged()
        }
        return inputList //tester om den kan returnere
    }

}