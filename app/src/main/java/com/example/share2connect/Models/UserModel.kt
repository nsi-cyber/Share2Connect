package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("userId")
    var id: Int?=null,

    @SerializedName("userNameText")
    var fullName: String?=null,

    @SerializedName("userImageUrl")
    var userImage: String?=null,

    @SerializedName("userMail")
    var email: String?=null,
    @SerializedName("userGender")
    var gender: String?=null,

    @SerializedName("userPassword")
    var password: String?=null,

    @SerializedName("userBio")
    var about: String?=null,

    @SerializedName("userPhoneNumber")
    var phone: String?=null,

    @SerializedName("userDepartment")
    var department: String?=null

)