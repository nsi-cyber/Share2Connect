package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("userId")
    var id: Int,

    @SerializedName("userNameText")
    var fullName: String,

    @SerializedName("Image")
    var Image: ByteArray,

    @SerializedName("userMail")
    var email: String,
    @SerializedName("userGender")
    var gender: String,

    @SerializedName("userPassword")
    var password: String,

    @SerializedName("userBio")
    var about: String,

    @SerializedName("userPhoneNumber")
    var phone: String,

    @SerializedName("userDepartment")
    var department: String

)