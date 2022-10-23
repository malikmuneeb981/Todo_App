package com.myproject.todoappwithnodejs.interfaces

import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo

interface TodoItemclicklistner {
    fun onItemclicked(todo: Todo)
    fun onitemlongclicked(todo: Todo)
    fun oneEditbuttonclicked(todo: Todo)
    fun onDonebuttonclicked(todo: Todo)
    fun ondDeletebuttonclicked(todo: Todo)
}