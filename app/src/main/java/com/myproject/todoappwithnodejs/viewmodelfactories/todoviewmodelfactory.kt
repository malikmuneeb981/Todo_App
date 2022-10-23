package com.myproject.todoappwithnodejs.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myproject.todoappwithnodejs.repository.todorepository
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.ui.FinishedTasks.FinishtasksViewModel
import com.myproject.todoappwithnodejs.ui.home.HomeViewModel


class todoviewmodelfactory(val application: Application, val todorepository: todorepository):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        HomeViewModel::class.java -> HomeViewModel(application = application, todorepository = todorepository)
        FinishtasksViewModel::class.java->FinishtasksViewModel(application = application, todorepository = todorepository)
//        initialassessmentinfoviewmodel::class.java -> initialassessmentinfoviewmodel(application = application, usersrepository = usersrepository)
//        addnewfamilyviewmodel::class.java -> addnewfamilyviewmodel(application = application,usersrepository=usersrepository)
//        communityandfamilyinfoviewmodel::class.java->communityandfamilyinfoviewmodel(application = application,usersrepository=usersrepository)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T



}

//class mainviewmodelfactory(val repository:userrepository):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return userviewmodel(repository) as T
//    }
//}