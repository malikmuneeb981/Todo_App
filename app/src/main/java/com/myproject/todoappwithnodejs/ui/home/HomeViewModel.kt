package com.myproject.todoappwithnodejs.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.myproject.todoappwithnodejs.repository.todorepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodobody
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodoresponse
import com.myproject.todoappwithnodejs.todomodels.DeleteTodo.Deletetodoresponse
import com.myproject.todoappwithnodejs.todomodels.FinishTodo.Finishtodobody
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Alltodosresponse
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodobody
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodoresponse
import kotlinx.coroutines.launch

class HomeViewModel(application: Application,val todorepository: todorepository) : AndroidViewModel(application) {


    val addtodotaskresponse=MutableLiveData<Baseresponse<Addtodoresponse>>()
    val alltodosresponse=MutableLiveData<Baseresponse<Alltodosresponse>>()
    val updatetodoresponse=MutableLiveData<Baseresponse<Updatetodoresponse>>()
    val deletetodoresponse=MutableLiveData<Baseresponse<Deletetodoresponse>>()

    fun addtodotask(addtodobody: Addtodobody,token:String)
    {
        viewModelScope.launch {
            try {
                val response=todorepository.addtodotask(token,addtodobody)

                if (response.code()==200)
                {
                    addtodotaskresponse.value=Baseresponse.Success(response.body())
                }
            }
            catch (e:Exception)
            {
                addtodotaskresponse.value=Baseresponse.Error(e.message.toString())
            }


        }
    }
    fun getalltodos(token: String)
    {
        viewModelScope.launch {
            try {
                val response=todorepository.getalltodos(token)
                if (response.code()==200)
                {
                    alltodosresponse.value=Baseresponse.Success(response.body())

                }

            }
            catch (e:Exception)
            {
             alltodosresponse.value=Baseresponse.Error(e.message.toString())
            }
        }
    }
    fun updatetodo(id:String,updatetodobody: Updatetodobody)
    {
        viewModelScope.launch {

            try {
                val response=todorepository.updatatodo(id = id, updatetodobody = updatetodobody)
                if (response.code()==200)
                {
                    updatetodoresponse.value=Baseresponse.Success(response.body())
                }

            }
            catch (e:Exception)
            {
                updatetodoresponse.value=Baseresponse.Error(e.message.toString())

            }
        }

    }
    fun deletetodo(id:String)
    {
        viewModelScope.launch {
            try {
                val response=todorepository.deletetodo(id)
                if (response.code()==200)
                {
                    deletetodoresponse.value=Baseresponse.Success(response.body())
                }

            }
            catch (e:Exception)
            {
                deletetodoresponse.value=Baseresponse.Error(e.message.toString())

            }
        }
    }
    fun finishtodo(id:String,finishtodobody: Finishtodobody)
    {
        viewModelScope.launch {
            try {
                val response=todorepository.finishtodo(id,finishtodobody)
                if (response.code()==200)
                {
                    updatetodoresponse.value=Baseresponse.Success(response.body())
                }

            }
            catch (e:Exception)
            {
                updatetodoresponse.value=Baseresponse.Error(e.message.toString())

            }
        }
    }








}