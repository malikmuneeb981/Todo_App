package com.myproject.todoappwithnodejs.database

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitinstance {

    fun getinstance(BASE_URL:String):Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()) .build()
    }
}