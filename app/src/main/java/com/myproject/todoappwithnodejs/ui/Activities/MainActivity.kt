package com.myproject.todoappwithnodejs.ui.Activities

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
import com.myproject.todoappwithnodejs.databinding.ActivityMainBinding
import com.myproject.todoappwithnodejs.models.registerbody
import com.myproject.todoappwithnodejs.models.userregresponse
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.viewmodelfactories.mainviewmodelfactory
import com.myproject.todoappwithnodejs.viewmodels.userviewmodel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var projectutils: projectutils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersdao=retrofitinstance.getinstance("https://scarlettodoapp.herokuapp.com/api/todo/auth/").create(userdao::class.java)

        val repository=userrepository(usersdao)
        val viewModel= ViewModelProvider(this, mainviewmodelfactory(application,repository))[userviewmodel::class.java]
        projectutils=projectutils()
        binding.btnregister.setOnClickListener {

            val email:String=binding.etemail.text.toString()
            val username:String=binding.etusername.text.toString()
            val password:String=binding.etpassword.text.toString()
            val registerbody=registerbody(email = email, password = password, username = username)
            viewModel.register(registerbody)
            viewModel.regResult.observe(this, Observer {
                when(it)
                {
                    is Baseresponse.Error -> {
                        Log.d("Error response", it.errormessage.toString())
                    }
                    is Baseresponse.Loading -> Log.d("Loading",it.toString())
                    is Baseresponse.Success -> { Log.d("Response",it.data.toString())
                        if (it.data?.success == true)
                        {
                            processLogin(it.data)
                           // startActivity(Intent(this,loginactivity::class.java))
                        }

                    }
                }
            })
        }
        binding.btnlogin.setOnClickListener {
            startActivity(Intent(this, loginactivity::class.java))
        }





    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences=applicationContext.getSharedPreferences("Todoappwithnodejs",MODE_PRIVATE)
        val token = PrefHelper.getauthtoken(this)
        Log.d("token",token.toString())
        if (sharedPreferences.contains(PrefHelper.USER_TOKEN))
        {
            startActivity(Intent(this, Homeactivity::class.java))
        }
    }
    fun processLogin(data: userregresponse?) {
        projectutils.showsnackbar(binding.clregister,data?.msg.toString())
//        showToast("Success:" + data?.message)
//        if (!data?.data?.token.isNullOrEmpty()) {
//            data?.data?.token?.let { SessionManager.saveAuthToken(this, it) }
//            navigateToHome()
//        }
    }
}