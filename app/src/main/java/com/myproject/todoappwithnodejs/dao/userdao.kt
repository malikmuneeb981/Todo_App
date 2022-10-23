package com.myproject.todoappwithnodejs.dao

import com.myproject.todoappwithnodejs.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface userdao {

    @POST("register")
    suspend fun register(@Body registerbody: registerbody):Response<userregresponse>

    @POST("login")
    suspend fun login(@Body loginbody: loginbody):Response<userloginresponse>

    @GET ("auth")
   suspend fun getuserprofileinfo(@Header("Authorization") token:String):Response<Userprofileinforesponse>
}