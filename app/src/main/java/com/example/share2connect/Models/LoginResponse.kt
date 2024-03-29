package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class LoginResponse (

        @SerializedName("status")
        var status: Int,

        @SerializedName("message")
        var message: String,

        @SerializedName("token")
        var token: String?=null,

        @SerializedName("user")
        var user: UserModel



)