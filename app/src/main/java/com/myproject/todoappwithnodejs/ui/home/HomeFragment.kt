package com.myproject.todoappwithnodejs.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.todoappwithnodejs.Adapters.Alltodosadapter
import com.myproject.todoappwithnodejs.R
import com.myproject.todoappwithnodejs.Utils.NetworkUtils
import com.myproject.todoappwithnodejs.Utils.PrefHelper
import com.myproject.todoappwithnodejs.Utils.projectutils
import com.myproject.todoappwithnodejs.dao.tododao
import com.myproject.todoappwithnodejs.database.retrofitinstance
import com.myproject.todoappwithnodejs.databinding.FragmentHomeBinding
import com.myproject.todoappwithnodejs.interfaces.TodoItemclicklistner
import com.myproject.todoappwithnodejs.repository.todorepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.todomodels.Addtodo.Addtodobody
import com.myproject.todoappwithnodejs.todomodels.FinishTodo.Finishtodobody
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo
import com.myproject.todoappwithnodejs.todomodels.UpdateTodo.Updatetodobody
import com.myproject.todoappwithnodejs.ui.Activities.loginactivity
import com.myproject.todoappwithnodejs.viewmodelfactories.todoviewmodelfactory
import kotlinx.android.synthetic.main.addtodocustomdialog.*
import kotlinx.android.synthetic.main.addtodocustomdialog.view.*
import kotlinx.android.synthetic.main.app_bar_homeactivity.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),TodoItemclicklistner {

    private var _binding: FragmentHomeBinding? = null
    lateinit var projectutils: projectutils
    lateinit var homeViewModel: HomeViewModel
    lateinit var Authtoken:String
    lateinit var todoslist:ArrayList<Todo>
    lateinit var adapter: Alltodosadapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.homeactivity, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_Refresh -> {
                getalltodos()
                true
            }
            R.id.action_Share -> {
                val intent=Intent(Intent.ACTION_SEND)
                intent.type="text/plain"
                val sharemsg="Hey try this to do app!"
                intent.putExtra(android.content.Intent.EXTRA_TEXT,sharemsg)
                startActivity(Intent.createChooser(intent,"Share Via"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val ADD_TODO_URL="https://scarlettodoapp.herokuapp.com/api/"
        val tododao=
            retrofitinstance.getinstance(ADD_TODO_URL).create(
                tododao::class.java)
        val todorepository= todorepository(tododao)
        homeViewModel=
            ViewModelProvider(this,
                todoviewmodelfactory(requireActivity().application,todorepository)
            )[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        todoslist= ArrayList()
        adapter= Alltodosadapter(this)
        Authtoken= PrefHelper.getauthtoken(requireContext()).toString()
        projectutils= projectutils()
        binding.btnaddtodo.setOnClickListener {
            showaddtodoalertdialog()
        }
        if (NetworkUtils.isNetworkAvailable(requireContext()))
        {
            getalltodos()
        }
        binding.swipeContainer.setOnRefreshListener {
            if (NetworkUtils.isNetworkAvailable(requireContext()))
            {
                getalltodos()
            }
            swipeContainer.isRefreshing=false
        }
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);








        return root
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId)
//        {
//            R.id.action_Refresh->{
//                getalltodos()
//                return true
//            }
//            else->{
//                return super.onOptionsItemSelected(item)
//            }
//        }
//
//    }
    private fun showaddtodoalertdialog() {
        val dialogview:View=layoutInflater.inflate(R.layout.addtodocustomdialog,null)

        val alertDialog=AlertDialog.Builder(activity).setView(dialogview).setTitle("Add Task").setPositiveButton("Add"){dialog,which->
            val title:String=dialogview.ettitle.text.toString()
            val description:String=dialogview.etdesc.text.toString()
            //val authtoken: String =PrefHelper.getauthtoken(requireContext()).toString()
            Log.d("Auth token",Authtoken)
           // projectutils.showsnackbar(binding.clhomefragment,"$title,$description")
            val addtodobody=Addtodobody(title = title, description = description)
            homeViewModel.addtodotask(addtodobody,Authtoken)
            homeViewModel.addtodotaskresponse.observe(requireActivity()) {
                Log.d("response",it.data.toString())
                when (it) {
                    is Baseresponse.Error -> {
                        Log.d("Error response", it.errormessage.toString())
                        projectutils.showsnackbar(binding.clhomefragment, it.errormessage.toString())
                    }
                    is Baseresponse.Loading -> Log.d("Response", "Loading")
                    is Baseresponse.Success -> {
                        Log.d("Response", it.data.toString())
                        it.data?.let { it1 ->
                            projectutils.showsnackbar(binding.clhomefragment,
                                it1.msg)
                            getalltodos()
                        }
                    }
                }
            }
        }.setNegativeButton("Cancel"){dialog,which->
            dialog.dismiss()

        }.create()


        alertDialog.show()

    }
    private fun ShowUpdateDialog(todo: Todo)
    {
        val updatedialog= layoutInflater.inflate(R.layout.addtodocustomdialog,null)
       updatedialog.ettitle.setText(todo.title)
        updatedialog.etdesc.setText(todo.description)
        val alertDialog=AlertDialog.Builder(requireActivity()).setView(updatedialog).setTitle("Update Todo Task").
                setPositiveButton("Update Task"){dialog,which->

                    val updatedtitle:String=updatedialog.ettitle.text.toString()
                    val updateddescription:String=updatedialog.etdesc.text.toString()
                    val updatetodobody=Updatetodobody(title = updatedtitle, description = updateddescription)
                    homeViewModel.updatetodo(id = todo._id, updatetodobody = updatetodobody)
                    homeViewModel.updatetodoresponse.observe(viewLifecycleOwner) {
                        when(it)
                        {
                            is Baseresponse.Error -> {
                                Log.d("error response",it.errormessage.toString())
                            }
                            is Baseresponse.Loading -> TODO()
                            is Baseresponse.Success -> {
                                Log.d("response",it.data.toString())
                                it.data?.let { it1 ->
                                    projectutils.showsnackbar(binding.clhomefragment,
                                        it1.msg)
                                }
                                getalltodos()
                            }
                        }

                    }
                }.setNegativeButton("Cancel"){dialog,which->
                    dialog.dismiss()
        }.create()
        alertDialog.show()
    }
    private fun ShowDeletedialog(todoid: String)
    {
        val alertDialog=AlertDialog.Builder(requireActivity()).setTitle("Delete Task?").setMessage("Confirm to Delete Task").setPositiveButton("Delete"){dialog,which->
            homeViewModel.deletetodo(todoid)
            homeViewModel.deletetodoresponse.observe(viewLifecycleOwner){
                when(it)
                {
                    is Baseresponse.Error -> {

                        Log.d("Error Response",it.errormessage.toString())
                    }
                    is Baseresponse.Loading -> {

                    }
                    is Baseresponse.Success -> {
                        Log.d("Response",it.data.toString())
                        it.data?.let { it1 ->
                            projectutils.showsnackbar(binding.clhomefragment,
                                it1.msg)
                        }
                        getalltodos()
                    }
                }
            }
        }.setNegativeButton("Cancel"){dialog,which->
           dialog.dismiss()
        }.create()
        alertDialog.show()

    }
    fun ShowFinishedDialog(todoid: String)
    {
        val alertDialog=AlertDialog.Builder(requireActivity()).setTitle("Finished Task?").setMessage("Confirm to Finish Task").setPositiveButton("Finish"){dialog,which->
            val finishtodobody=Finishtodobody("true")
            homeViewModel.finishtodo(todoid,finishtodobody)
            homeViewModel.updatetodoresponse.observe(viewLifecycleOwner){
                when(it)
                {
                    is Baseresponse.Error -> {

                        Log.d("Error Response",it.errormessage.toString())
                    }
                    is Baseresponse.Loading -> {

                    }
                    is Baseresponse.Success -> {
                        Log.d("Response",it.data.toString())
                        it.data?.let { it1 ->
                            projectutils.showsnackbar(binding.clhomefragment,
                                it1.msg)
                        }
                        getalltodos()
                    }
                }
            }
        }.setNegativeButton("Cancel"){dialog,which->
            dialog.dismiss()
        }.create()
        alertDialog.show()

    }
    private fun getalltodos()
    {

        homeViewModel.getalltodos(Authtoken)
        homeViewModel.alltodosresponse.observe(requireActivity(), Observer {
            when(it)
            {
                is Baseresponse.Error -> {
                    binding.pbhomefrag.visibility=View.GONE

                    PrefHelper.clearData(requireContext())
                    startActivity(Intent(requireActivity(),loginactivity::class.java))
                    Toast.makeText(requireContext(), "Session Expired Login Again", Toast.LENGTH_LONG).show()

                    Log.d("error response", it.errormessage.toString())

                }
                is Baseresponse.Loading -> {
                    Log.d("Loading", "Loading")
                    binding.pbhomefrag.visibility=View.VISIBLE

                }
                is Baseresponse.Success -> {
                    binding.pbhomefrag.visibility=View.GONE
                    Log.d("response", it.data?.todos.toString())
                    it.data?.todos?.forEach{

                        todoslist.add(it)

                    }
                    adapter.gettodoitems(todoslist)
                    todoslist.clear()

                }
            }
        })
        binding.rvalltodos.layoutManager= LinearLayoutManager(activity)
        binding.rvalltodos.adapter=adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemclicked(todo: Todo) {
        Log.d("item clicked",todo.toString())
    }

    override fun onitemlongclicked(todo: Todo) {
        Log.d("item long clicked",todo.toString())
    }

    override fun oneEditbuttonclicked(todo: Todo) {
        Log.d("edit btn clicked",todo.toString())
        val todoid:String=todo._id
        ShowUpdateDialog(todo)
    }

    override fun onDonebuttonclicked(todo: Todo) {
        Log.d("done btn clicked",todo.toString())
        val todoid:String=todo._id
        ShowFinishedDialog(todoid)
    }

    override fun ondDeletebuttonclicked(todo: Todo) {
        Log.d("delete btn clicked",todo.toString())
        val todoid:String=todo._id
        ShowDeletedialog(todoid)
    }
}