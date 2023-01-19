package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse (

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("user")
    var user: UserModel,


    )