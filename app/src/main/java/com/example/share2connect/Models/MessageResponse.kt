package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class MessageResponse (

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,


)