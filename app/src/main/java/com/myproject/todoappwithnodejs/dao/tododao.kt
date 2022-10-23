package com.myproject.todoappwithnodejs.dao

import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodobody
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodoresponse
import com.myproject.todoappwithnodejs.todomodels.DeleteTodo.Deletetodoresponse
import com.myproject.todoappwithnodejs.todomodels.FinishTodo.Finishtodobody
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Alltodosresponse
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodobody
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodoresponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface tododao {


    @Headers("Content-type:application/json")
    @POST("todo")
    suspend fun addtodotask(@Header("Authorization") token:String,@Body addtodobody: Addtodobody):Response<Addtodoresponse>

    @GET("todo")
    suspend fun getalltodos(@Header("Authorization") token:String):Response<Alltodosresponse>

    @PUT("todo/{id}")
    suspend fun updatetodo(@Path("id") id:String,@Body updatetodobody: Updatetodobody):Response<Updatetodoresponse>

    @DELETE("todo/{id}")
    suspend fun deletetodo (@Path("id") id:String):Response<Deletetodoresponse>

    @PUT("todo/{id}")
    suspend fun finishtodo(@Path("id") id:String,@Body finishtodobody: Finishtodobody):Response<Updatetodoresponse>

    @GET("finished")
    suspend fun getfinishedtodos(@Header("Authorization")token:String):Response<Alltodosresponse>

    @DELETE("{id}")
    suspend fun deletefinishedtodo (@Path("id") id:String):Response<Deletetodoresponse>


}