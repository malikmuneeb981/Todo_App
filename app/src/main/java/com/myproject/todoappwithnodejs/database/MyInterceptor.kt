//package com.myproject.todoappwithnodejs.database
//
//import okhttp3.Interceptor
//import okhttp3.Response
//
//class MyInterceptor(token:String):Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request=chain.request().newBuilder().addHeader("Content-Type","application/json")
//    }
//}