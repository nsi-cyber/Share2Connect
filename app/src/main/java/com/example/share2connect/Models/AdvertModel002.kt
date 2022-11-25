package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class AdvertModel002 (


    @SerializedName("publishDate")
    var publishDate: String,

    @SerializedName("isAvailable")
    var isAvailable: Boolean,

    @SerializedName("title")
    var title: String,

    @SerializedName("desc")
    var desc: String,

    @SerializedName("fee")
    var fee: String,

    @SerializedName("time")
    var time: String,

    @SerializedName("date")
    var date: String,

    @SerializedName("photo")
    var photo: String,

    @SerializedName("gpsName")
    var gpsName: String,

    @SerializedName("gpsCoordinate")
    var gpsCoordinate: String,

    )