package com.example.bitirmeprojesi.Models

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("id")
    var id: String,

    @SerializedName("fullName")
    var fullName: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("gender")
    var gender: String

)