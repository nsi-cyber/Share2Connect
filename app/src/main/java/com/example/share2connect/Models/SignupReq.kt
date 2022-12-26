package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class SignupReq (

    @SerializedName("userNameText")
    var userNameText: String,


    @SerializedName("userMail")
    var userMail: String,
    @SerializedName("userGender")
    var userGender: String,

    @SerializedName("userPassword")
    var userPassword: String,

    @SerializedName("userDepartment")
    var userDepartment: String,

    @SerializedName("userBio")
    var userBio: String,

    @SerializedName("userImage")
    var userImage: ByteArray?=null,

    @SerializedName("userPhoneNumber")
    var userPhoneNumber: String,


)