package com.example.dummyproject.opentdb

import com.example.dummyproject.opentdb.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val openTDB: OpenTDB by lazy {
        retrofit.create(OpenTDB::class.java)
    }

}