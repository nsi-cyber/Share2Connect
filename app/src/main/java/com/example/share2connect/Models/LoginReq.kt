package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class LoginReq (

    @SerializedName("userMail")
    var userMail: String,

    @SerializedName("userPassword")
    var userPassword: String
)