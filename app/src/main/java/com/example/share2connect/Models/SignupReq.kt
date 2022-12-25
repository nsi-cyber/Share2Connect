package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class SignupReq (

    @SerializedName("userNameText")
    var fullName: String,


    @SerializedName("userMail")
    var email: String,
    @SerializedName("userGender")
    var gender: String,

    @SerializedName("userPassword")
    var password: String,

    @SerializedName("userDepartment")
    var department: String,

    @SerializedName("userBio")
    var about: String,

    @SerializedName("userImage")
    var image: ByteArray,

    @SerializedName("userPhoneNumber")
    var phone: String,


)