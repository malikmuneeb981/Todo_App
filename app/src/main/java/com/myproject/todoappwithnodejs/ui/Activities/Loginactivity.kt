package com.myproject.todoappwithnodejs.ui.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myproject.todoappwithnodejs.Utils.PrefHelper
import com.myproject.todoappwithnodejs.Utils.projectutils
import com.myproject.todoappwithnodejs.dao.userdao
import com.myproject.todoappwithnodejs.database.retrofitinstance
import com.myproject.todoappwithnodejs.databinding.ActivityLoginactivityBinding
import com.myproject.todoappwithnodejs.models.loginbody
import com.myproject.todoappwithnodejs.models.userloginresponse
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.viewmodelfactories.mainviewmodelfactory
import com.myproject.todoappwithnodejs.viewmodels.userviewmodel

class loginactivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginactivityBinding
    lateinit var projectutils:projectutils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val BASE_URL=""
        val userdao=retrofitinstance.getinstance("https://scarlettodoapp.herokuapp.com/api/todo/auth/").create(userdao::class.java)
        val userrepository=userrepository(userdao)
        val viewModel=
            ViewModelProvider(this, mainviewmodelfactory(application = application,userrepository))[userviewmodel::class.java]
        projectutils=projectutils()

        binding.btnlogin1.setOnClickListener {
            val email:String=binding.etemaillogin.text.toString()
            val password:String=binding.etpasswordlogin.text.toString()
            val loginbody=loginbody(email = email, password = password)
            viewModel.login(loginbody)
            viewModel.loginresult.observe(this, Observer {
                when(it)
                {
                    is Baseresponse.Error -> {


                    }
                    is Baseresponse.Loading -> {


                    }
                    is Baseresponse.Success -> {
                        Log.d("response", it.data.toString())
                        if (it.data?.success == true)
                        {
                              processlogin(it.data)
                        }
                        if (it.data == null)
                        {
                            projectutils.showsnackbar(binding.cllogin, "User does not exists")
                        }

                    }
                }
            })
        }
    }
    fun processlogin(data: userloginresponse?)
    {
        if (data != null) {
            projectutils.showsnackbar(binding.cllogin, data.msg)
        }
        if (!data?.token.isNullOrEmpty())
        {
            data?.token?.let { PrefHelper.saveauthtoken(this, it) }
            startActivity(Intent(this, Homeactivity::class.java))
        }

       // if ()
    }
}