package com.myproject.todoappwithnodejs.repository

import com.myproject.todoappwithnodejs.dao.tododao
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodobody
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodoresponse
import com.myproject.todoappwithnodejs.todomodels.DeleteTodo.Deletetodoresponse
import com.myproject.todoappwithnodejs.todomodels.FinishTodo.Finishtodobody
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Alltodosresponse
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodobody
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodoresponse
import retrofit2.Response

class todorepository(val tododao: tododao) {

    suspend fun addtodotask(token:String,addtodobody: Addtodobody):Response<Addtodoresponse>
    {
        return tododao.addtodotask(token,addtodobody)
    }
    suspend fun getalltodos(token: String):Response<Alltodosresponse>
    {
        return tododao.getalltodos(token)
    }
    suspend fun updatatodo(id:String,updatetodobody: Updatetodobody):Response<Updatetodoresponse>
    {
        return tododao.updatetodo(id,updatetodobody)
    }
    suspend fun deletetodo(id:String):Response<Deletetodoresponse>
    {
        return tododao.deletetodo(id)
    }
    suspend fun finishtodo(id:String,finishtodobody: Finishtodobody):Response<Updatetodoresponse>
    {
        return tododao.finishtodo(id = id, finishtodobody = finishtodobody)
    }
    suspend fun getfinishedtodos(token:String):Response<Alltodosresponse>
    {
        return tododao.getfinishedtodos(token)
    }
    suspend fun deletefinishedtodo(id:String):Response<Deletetodoresponse>
    {
        return tododao.deletefinishedtodo(id)
    }

}