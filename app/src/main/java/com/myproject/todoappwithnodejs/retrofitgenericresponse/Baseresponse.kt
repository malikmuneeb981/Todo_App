package com.myproject.todoappwithnodejs.retrofitgenericresponse

sealed class Baseresponse<T>(val data:T?=null, val errormessage:String?=null)
{
    class Loading<T>:Baseresponse<T>()
    class Success<T>(data: T?=null):Baseresponse<T>(data=data)
    class Error<T>(errormessage: String):Baseresponse<T>(errormessage=errormessage)
}
