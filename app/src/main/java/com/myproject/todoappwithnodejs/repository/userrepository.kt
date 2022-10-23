package com.myproject.todoappwithnodejs.repository

import androidx.lifecycle.MutableLiveData
import com.myproject.todoappwithnodejs.dao.userdao
import com.myproject.todoappwithnodejs.database.retrofitinstance
import com.myproject.todoappwithnodejs.models.*
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import retrofit2.Response

class userrepository(val userdao: userdao) {

    val userregresponselivedata = MutableLiveData<Baseresponse<userregresponse>>()

    suspend fun register(registerbody: registerbody):Response<userregresponse>? {
        return  userdao.register(registerbody)
    }
    suspend fun login(loginbody: loginbody):Response<userloginresponse>?{
        return userdao.login(loginbody)
    }
    suspend fun getuserprofileinfo(token:String):Response<Userprofileinforesponse>
    {
        return userdao.getuserprofileinfo(token)
    }
}