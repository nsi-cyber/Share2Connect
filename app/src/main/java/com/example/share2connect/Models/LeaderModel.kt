package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

class LeaderModel  (


    @SerializedName("id")
    var id: Int,

    @SerializedName("user")
    var user: UserModel,

    @SerializedName("puan")
    var puan: Int,



    )