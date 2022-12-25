package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class SignupReq (

    @SerializedName("FullName")
    var FullName: String,

    @SerializedName("Email")
    var Email: String,
    @SerializedName("Gender")
    var Gender: String,

    @SerializedName("Password")
    var Password: String,

    @SerializedName("About")
    var About: String,

    @SerializedName("Department")
    var Department: String

)