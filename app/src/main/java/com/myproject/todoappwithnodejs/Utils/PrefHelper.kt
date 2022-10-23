package com.myproject.todoappwithnodejs.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.myproject.todoappwithnodejs.R

object PrefHelper {

    const val USER_TOKEN="user_token"


    fun saveauthtoken(context: Context,token:String)
    {
           savestring(context, USER_TOKEN,token)
    }

    fun getauthtoken(context: Context):String?
    {
         return getstring(context, USER_TOKEN)
    }
    fun clearData(context: Context){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }






    @SuppressLint("CommitPrefEdits")
    fun savestring(context: Context, key:String, value:String)
    {
       val sharedPreferences= context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun getstring(context: Context,key:String):String?
    {
        val sharedPreferences=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
        return sharedPreferences.getString(this.USER_TOKEN,null)
    }
}