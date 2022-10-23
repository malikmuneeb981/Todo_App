package com.myproject.todoappwithnodejs.interfaces

import com.myproject.todoappwithnodejs.todomodels.GetAllTodos.Todo

interface FinishedTodoItemclicklistner {
    fun onItemclicked(todo: Todo)
    fun onitemlongclicked(todo: Todo)
    fun ondDeletebuttonclicked(todo: Todo)
}