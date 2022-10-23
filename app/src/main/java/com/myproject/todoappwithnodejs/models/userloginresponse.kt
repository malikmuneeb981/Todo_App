package com.myproject.todoappwithnodejs.models

data class userloginresponse(
    val msg: String,
    val success: Boolean,
    val token: String,
    val user: UserX
)