package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class UserModel (
    @SerializedName("id")
    var id: Int,

    @SerializedName("FullName")
    var FullName: String,

    @SerializedName("Image")
    var Image: ByteArray,

    @SerializedName("Email")
    var Email: String,
    @SerializedName("Gender")
    var Gender: String,

    @SerializedName("Password")
    var Password: String,

    @SerializedName("About")
    var About: String,

    @SerializedName("Phone")
    var Phone: String,

    @SerializedName("Department")
    var Department: String

)