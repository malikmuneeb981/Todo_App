package com.myproject.todoappwithnodejs.ui.Activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.myproject.todoappwithnodejs.R
import com.myproject.todoappwithnodejs.Utils.PrefHelper
import com.myproject.todoappwithnodejs.Utils.projectutils
import com.myproject.todoappwithnodejs.dao.userdao
import com.myproject.todoappwithnodejs.database.retrofitinstance
import com.myproject.todoappwithnodejs.databinding.ActivityHomeactivityBinding
import com.myproject.todoappwithnodejs.repository.userrepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.viewmodelfactories.mainviewmodelfactory
import com.myproject.todoappwithnodejs.viewmodels.userviewmodel
import kotlinx.android.synthetic.main.nav_header_homeactivity.view.*

class Homeactivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeactivityBinding
    lateinit var viewmodel:userviewmodel
    lateinit var Auth_token:String
    lateinit var navheader:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeactivity.mytoolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_homeactivity)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        Auth_token= PrefHelper.getauthtoken(this).toString()
        val BASE_URL:String="https://scarlettodoapp.herokuapp.com/api/todo/"
        val userdao=retrofitinstance.getinstance(BASE_URL).create(userdao::class.java)
        val userrepository=userrepository(userdao)
        viewmodel = ViewModelProvider(this,mainviewmodelfactory(application,userrepository))[userviewmodel::class.java]
        getuserprofileinfo()
        navheader=binding.navView.getHeaderView(0)



    }
    fun setActionBarTitle(title: String?) {
        supportActionBar?.title=(title)
    }

    private fun getuserprofileinfo() {


        viewmodel.userprofileinfo(Auth_token)
        viewmodel.userprofileinfo.observe(this){
            when(it)
            {
                is Baseresponse.Error -> {
                    Log.d("error response",it.errormessage.toString())

                }
                is Baseresponse.Loading -> {

                }
                is Baseresponse.Success -> {
                    Log.d("response",it.data.toString())
                    if (it.data!=null) {
                        navheader.tvuseremail.text = it.data.user.email
                        navheader.tvusername.text = it.data.user.username
                        val avatar:String=it.data.user.avatar
                        Glide.with(this).load(avatar).into(navheader.ivuseravatar)
                    }

                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.homeactivity, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_homeactivity)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}