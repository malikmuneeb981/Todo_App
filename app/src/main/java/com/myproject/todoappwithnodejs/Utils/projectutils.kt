package com.myproject.todoappwithnodejs.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar

class projectutils {


    fun showsnackbar(view: View,message:String)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
    }

}