package com.myproject.todoappwithnodejs.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.myproject.todoappwithnodejs.R
import com.myproject.todoappwithnodejs.interfaces.FinishedTodoItemclicklistner
import com.myproject.todoappwithnodejs.interfaces.TodoItemclicklistner
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo
import kotlinx.android.synthetic.main.finishedtodorvitem.view.*
import kotlinx.android.synthetic.main.todorvitem.view.*
import java.util.Random

class Finishedtodosadapter(val clicklistner:FinishedTodoItemclicklistner):RecyclerView.Adapter<Finishedtodosadapter.finishedtodosviewholder>() {

    var finishedtodosarraylist=ArrayList<Todo>()
    lateinit var context:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): finishedtodosviewholder {
        context=parent.context
        val view=LayoutInflater.from(parent.context).inflate(R.layout.finishedtodorvitem,parent,false)
        val finishedtodosviewholder=finishedtodosviewholder(view)
        val androidColors=view.resources.getIntArray(R.array.androidcolors)
        val randomColors:Int=androidColors[Random().nextInt(androidColors.size)]
        finishedtodosviewholder.accordian_title.setBackgroundColor(randomColors)
        view.finished_arrow.setOnClickListener {
            if (view.finished_accordian_body.visibility== View.VISIBLE)
            {
                finishedtodosviewholder.accordian_body.visibility=View.GONE
            }
            else
            {
                finishedtodosviewholder.accordian_body.visibility=View.VISIBLE
            }
        }
        view.setOnClickListener {
            clicklistner.onItemclicked(finishedtodosarraylist[finishedtodosviewholder.adapterPosition])
        }
        view.setOnLongClickListener {
            clicklistner.onitemlongclicked(finishedtodosarraylist[finishedtodosviewholder.adapterPosition])
            return@setOnLongClickListener true

        }
        view.finisheddeleteBtn.setOnClickListener {
            clicklistner.ondDeletebuttonclicked(finishedtodosarraylist[finishedtodosviewholder.adapterPosition])
        }



        return finishedtodosviewholder

    }

    override fun onBindViewHolder(holder: finishedtodosviewholder, position: Int) {
        val currenttodoitem=finishedtodosarraylist[position]
        holder.tasktitle.text=currenttodoitem.title
        holder.taskdescription.text=currenttodoitem.description

    }

    override fun getItemCount(): Int {
       return finishedtodosarraylist.size
    }

    fun getfinishedtodoitems(todolist:ArrayList<Todo>)
    {
        finishedtodosarraylist.clear()
        finishedtodosarraylist.addAll(todolist)
        notifyDataSetChanged()

    }






    class finishedtodosviewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        val tasktitle:TextView=itemView.findViewById(R.id.finished_task_title)
        val taskdescription:TextView=itemView.findViewById(R.id.finished_task_description)
        val accordian_title:CardView=itemView.findViewById(R.id.finished_accordian_title)
        val accordian_body:RelativeLayout=itemView.findViewById(R.id.finished_accordian_body)
        val btndelete:ImageView=itemView.findViewById(R.id.finisheddeleteBtn)








    }
}