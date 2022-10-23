package com.myproject.todoappwithnodejs.ui.FinishedTasks

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.todoappwithnodejs.Adapters.Finishedtodosadapter
import com.myproject.todoappwithnodejs.R
import com.myproject.todoappwithnodejs.Utils.NetworkUtils
import com.myproject.todoappwithnodejs.Utils.PrefHelper
import com.myproject.todoappwithnodejs.Utils.projectutils
import com.myproject.todoappwithnodejs.dao.tododao
import com.myproject.todoappwithnodejs.database.retrofitinstance
import com.myproject.todoappwithnodejs.databinding.FragmentFinishedtasksfragmentBinding
import com.myproject.todoappwithnodejs.interfaces.FinishedTodoItemclicklistner
import com.myproject.todoappwithnodejs.repository.todorepository
import com.myproject.todoappwithnodejs.retrofitgenericresponse.Baseresponse
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo
import com.myproject.todoappwithnodejs.ui.Activities.Homeactivity
import com.myproject.todoappwithnodejs.ui.Activities.loginactivity
import com.myproject.todoappwithnodejs.viewmodelfactories.todoviewmodelfactory
import kotlinx.android.synthetic.main.app_bar_homeactivity.*


class finishedtasksfragment : Fragment(),FinishedTodoItemclicklistner {

    private var _binding: FragmentFinishedtasksfragmentBinding? = null
    lateinit var projectutils: projectutils
    lateinit var finishedtasksviewmodel: FinishtasksViewModel
    lateinit var Authtoken:String
    lateinit var finishedtodoslist:ArrayList<Todo>
    lateinit var adapter: Finishedtodosadapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val BASE_URL="https://scarlettodoapp.herokuapp.com/api/todo/"
        val tododao=
            retrofitinstance.getinstance(BASE_URL).create(
                tododao::class.java)
        val todorepository= todorepository(tododao)
        finishedtasksviewmodel=
            ViewModelProvider(this,
                todoviewmodelfactory(requireActivity().application,todorepository)
            )[FinishtasksViewModel::class.java]
        _binding = FragmentFinishedtasksfragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as Homeactivity?)?.setActionBarTitle("Finished")
        finishedtodoslist= ArrayList()
        adapter= Finishedtodosadapter(this)
        Authtoken= PrefHelper.getauthtoken(requireContext()).toString()
        projectutils= projectutils()
        if (NetworkUtils.isNetworkAvailable(requireContext()))
        {
            getfinishedalltodos()
        }
        binding.finishedswipecontainer.setOnRefreshListener {
            if (NetworkUtils.isNetworkAvailable(requireContext()))
            {
                getfinishedalltodos()
            }
            binding.finishedswipecontainer.isRefreshing=false
        }



        return root
    }
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
                getfinishedalltodos()
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
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId)
//        {
//            R.id.action_Refresh->{
//                Log.d("refresh","refresh todo")
//                getfinishedalltodos()
//                return true
//            }
//            else -> {return super.onOptionsItemSelected(item)}
//        }
//    }
    fun getfinishedalltodos()
    {
        finishedtasksviewmodel.getfinishedtodos(Authtoken)
        finishedtasksviewmodel.finishedtodoresponse.observe(requireActivity()){
            when(it)
            {
                is Baseresponse.Error -> {
                    binding.pbfinishedtodosfrag.visibility=View.GONE

                    PrefHelper.clearData(requireContext())
                    startActivity(Intent(requireActivity(), loginactivity::class.java))
                    Toast.makeText(requireContext(), "Session Expired Login Again", Toast.LENGTH_LONG).show()

                    Log.d("error response", it.errormessage.toString())

                }
                is Baseresponse.Loading -> {
                    Log.d("Loading", "Loading")
                    binding.pbfinishedtodosfrag.visibility=View.VISIBLE

                }
                is Baseresponse.Success -> {
                    binding.pbfinishedtodosfrag.visibility=View.GONE
                    Log.d("response", it.data?.todos.toString())
                    it.data?.todos?.forEach{

                        finishedtodoslist.add(it)

                    }
                    adapter.getfinishedtodoitems(finishedtodoslist)
                    finishedtodoslist.clear()

                }
            }
        }
        binding.rvfinishedtodos.layoutManager= LinearLayoutManager(activity)
        binding.rvfinishedtodos.adapter=adapter

    }
    fun ShowDeletedialog(todo:Todo)
    {
        val alertDialog= AlertDialog.Builder(requireActivity()).setTitle("Delete Finished Task?").setMessage("Confirm to Delete Task").setPositiveButton("Delete"){ dialog, which->
            finishedtasksviewmodel.deletetodo(todo._id)
           finishedtasksviewmodel.deletetodoresponse.observe(viewLifecycleOwner){
                when(it)
                {
                    is Baseresponse.Error -> {

                        Log.d("Error Response",it.errormessage.toString())
                    }
                    is Baseresponse.Loading -> {

                    }
                    is Baseresponse.Success -> {
                        Log.d("Response",it.data.toString())
                    }
                }
            }
        }.setNegativeButton("Cancel"){dialog,which->
            dialog.dismiss()
        }.create()
        alertDialog.show()
    }

    override fun onItemclicked(todo: Todo) {
        TODO("Not yet implemented")
    }

    override fun onitemlongclicked(todo: Todo) {
        TODO("Not yet implemented")
    }

    override fun ondDeletebuttonclicked(todo: Todo) {
        ShowDeletedialog(todo)
    }


}