package com.example.chat.model

import android.R

data class Message(
    val id : String = "" ,
    val  senderId : String = "" ,
    val message: String? = "",
    val createdAt : String = "" ,
    val senderName: String = "" ,
    val  senderIMage: String? = null ,
    val imageUrl : String?  = null ,
    )