package com.myproject.todoappwithnodejs.todomodels.GetAllTodos

data class Todo(
    val __v: Int,
    val _id: String,
    val createdat: String,
    val description: String,
    val finished: Boolean,
    val title: String,
    val user: String
)