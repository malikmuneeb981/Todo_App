package com.myproject.todoappwithnodejs.ui.FinishedTasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myproject.todoappwithnodejs.repository.todorepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.todomodels.DeleteTodo.Deletetodoresponse
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Alltodosresponse
import kotlinx.coroutines.launch

class FinishtasksViewModel(application: Application,val todorepository: todorepository):AndroidViewModel(application) {



    val finishedtodoresponse=MutableLiveData<Baseresponse<Alltodosresponse>>()
    val deletetodoresponse=MutableLiveData<Baseresponse<Deletetodoresponse>>()

    fun getfinishedtodos(token:String)
    {
        viewModelScope.launch {
            try {
                val response = todorepository.getfinishedtodos(token)
                if (response.code()==200)
                {
                    finishedtodoresponse.value=Baseresponse.Success(response.body())
                }
            }
            catch (e:Exception)
            {
                finishedtodoresponse.value=Baseresponse.Error(e.message.toString())
            }
        }
    }
    fun deletetodo(id:String)
    {
        viewModelScope.launch {
            try {
                val response=todorepository.deletefinishedtodo(id)
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
}