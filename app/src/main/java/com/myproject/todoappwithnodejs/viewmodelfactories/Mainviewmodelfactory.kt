package com.myproject.todoappwithnodejs.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.viewmodels.userviewmodel


class mainviewmodelfactory(val application: Application, val usersrepository: userrepository):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        userviewmodel::class.java -> userviewmodel(usersrepository,application)
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