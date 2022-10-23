package com.myproject.todoappwithnodejs.todomodels.Addtodo

data class Addtodoresponse(
    val msg: String,
    val success: Boolean,
    val todo: Todo
)