package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse (

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("data")
    var data: ArrayList<LeaderModel>



)