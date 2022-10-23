package com.myproject.todoappwithnodejs.models

data class userregresponse(
    val msg: String,
    val success: Boolean,
    val user: User
)