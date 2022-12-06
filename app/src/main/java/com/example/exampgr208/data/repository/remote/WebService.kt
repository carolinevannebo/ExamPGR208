package com.example.exampgr208.data.repository.remote

import com.example.exampgr208.data.RecipeList
import com.example.exampgr208.data.repository.MainRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WebService {

    private val api: Api by lazy { createApi() }

    suspend fun getRecipes(): RecipeList {
        return api.getRecipes()
    }

    private fun createApi(): Api {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            //.baseUrl(MainRepository.URL)
            .baseUrl(MainRepository.TEST_EXAMPLE_URL) // skal være URL ikke test, men skjønner ikke endepunktet
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(Api::class.java)
    }
}