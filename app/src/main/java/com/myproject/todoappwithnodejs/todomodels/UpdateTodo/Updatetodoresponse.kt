package com.myproject.todoappwithnodejs.todomodels.UpdateTodo

data class Updatetodoresponse(
    val msg: String,
    val success: Boolean,
    val todo: Todo
)