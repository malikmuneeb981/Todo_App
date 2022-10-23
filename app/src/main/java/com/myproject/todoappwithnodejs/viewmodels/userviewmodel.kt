package com.myproject.todoappwithnodejs.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.todoappwithnodejs.models.*
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class userviewmodel(val userrepository: userrepository,application: Application) :AndroidViewModel(application){


    val regResult: MutableLiveData<Baseresponse<userregresponse>> = MutableLiveData()
    val loginresult:MutableLiveData<Baseresponse<userloginresponse>> = MutableLiveData()
    val userprofileinfo=MutableLiveData<Baseresponse<Userprofileinforesponse>>()
    fun register(registerbody: registerbody){
        viewModelScope.launch{
            try {


                // userrepository.register(registerbody)
                val response = userrepository.register(registerbody)
                if (response?.code() == 200) {
                    regResult.value = Baseresponse.Success(response.body())
                }
                if (response?.code() == 400) {
                    regResult.value = Baseresponse.Success(response.body())
                }
            }catch (e:Exception)
            {
                regResult.value=Baseresponse.Error(e.message.toString())

            }
        }
    }
    fun login(loginbody: loginbody){
        viewModelScope.launch {
            try {
                val response=userrepository.login(loginbody)
                Log.d("loginresponse",response?.body().toString())
                if (response?.code()==200)
                {
                    loginresult.value=Baseresponse.Success(response.body())
                }
                if (response?.code()==401)
                {
                    loginresult.value=Baseresponse.Error("Authorization failed token invalid")
                }



            }
            catch (e:Exception)
            {
                loginresult.value=Baseresponse.Error(e.toString())

            }
        }
    }
    fun userprofileinfo(token:String)
    {
        viewModelScope.launch {
            try {
                val response=userrepository.getuserprofileinfo(token)
                if (response.code()==200)
                {
                    userprofileinfo.value=Baseresponse.Success(response.body())
                }
            }
            catch (e:Exception)
            {
                userprofileinfo.value=Baseresponse.Error(e.message.toString())

            }
        }
    }



}