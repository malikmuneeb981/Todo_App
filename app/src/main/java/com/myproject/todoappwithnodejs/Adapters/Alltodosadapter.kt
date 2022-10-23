package com.myproject.todoappwithnodejs.Adapters

import android.annotation.SuppressLint
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
import com.myproject.todoappwithnodejs.interfaces.TodoItemclicklistner
import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo
import kotlinx.android.synthetic.main.todorvitem.view.*
import java.util.*

class Alltodosadapter(val clicklistner:TodoItemclicklistner):RecyclerView.Adapter<Alltodosadapter.alltodosviewholder>() {

    var todosarraylist=ArrayList<Todo>()
    lateinit var context:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): alltodosviewholder {
        context=parent.context
        val view=LayoutInflater.from(parent.context).inflate(R.layout.todorvitem,parent,false)
        val alltodosviewholder=alltodosviewholder(view)
        val androidColors=view.resources.getIntArray(R.array.androidcolors)
        val randomColors:Int=androidColors[Random().nextInt(androidColors.size)]
        alltodosviewholder.accordian_title.setBackgroundColor(randomColors)
        view.arrow.setOnClickListener {
            if (view.accordian_body.visibility== View.VISIBLE)
            {
                alltodosviewholder.accordian_body.visibility=View.GONE
            }
            else
            {
                alltodosviewholder.accordian_body.visibility=View.VISIBLE
            }
        }
        view.setOnClickListener {
            clicklistner.onItemclicked(todosarraylist[alltodosviewholder.adapterPosition])
        }
        view.setOnLongClickListener {
            clicklistner.onitemlongclicked(todosarraylist[alltodosviewholder.adapterPosition])
            return@setOnLongClickListener true

        }
        view.editBtn.setOnClickListener {
            clicklistner.oneEditbuttonclicked(todosarraylist[alltodosviewholder.adapterPosition])
        }
        view.doneBtn.setOnClickListener {
            clicklistner.onDonebuttonclicked(todosarraylist[alltodosviewholder.adapterPosition])
        }
        view.deleteBtn.setOnClickListener {
            clicklistner.ondDeletebuttonclicked(todosarraylist[alltodosviewholder.adapterPosition])
        }



        return alltodosviewholder

    }

    override fun onBindViewHolder(holder: alltodosviewholder, position: Int) {
        val currenttodoitem=todosarraylist[position]
        holder.tasktitle.text=currenttodoitem.title
        holder.taskdescription.text=currenttodoitem.description

    }

    override fun getItemCount(): Int {
       return todosarraylist.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun gettodoitems(todolist:ArrayList<Todo>)
    {
        todosarraylist.clear()
        todosarraylist.addAll(todolist)
        notifyDataSetChanged()

    }
    fun clear() {
        todosarraylist.clear()
        notifyDataSetChanged()
    }






    class alltodosviewholder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        val tasktitle:TextView=itemView.findViewById(R.id.task_title)
        val taskdescription:TextView=itemView.findViewById(R.id.task_description)
        val accordian_title:CardView=itemView.findViewById(R.id.accordian_title)
        val accordian_body:RelativeLayout=itemView.findViewById(R.id.accordian_body)
        val btndelete:ImageView=itemView.findViewById(R.id.deleteBtn)
        val btnedit:ImageView=itemView.findViewById(R.id.editBtn)
        val btnfinished:ImageView=itemView.findViewById(R.id.doneBtn)







    }
}