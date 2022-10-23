package com.myproject.todoappwithnodejs.todomodels.GetAllTodos

data class Alltodosresponse(
    val msg: String,
    val success: Boolean,
    val todos: List<Todo>
)