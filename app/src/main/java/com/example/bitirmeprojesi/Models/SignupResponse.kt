package com.example.bitirmeprojesi.Models

import com.google.gson.annotations.SerializedName


data class SignupResponse (

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("id")
    var id: String,

    @SerializedName("fullName")
    var fullName: String,

    @SerializedName("email")
    var email: String,


    @SerializedName("gender")
    var gender: String,

    @SerializedName("token")
    var token: String

)