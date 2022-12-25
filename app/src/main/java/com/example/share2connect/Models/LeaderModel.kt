package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

class LeaderModel  (


    @SerializedName("name")
    var name: String,

    @SerializedName("pos")
    var pos: String,

    @SerializedName("image")
    var image: ByteArray,

    @SerializedName("point")
    var point: String,

    @SerializedName("dep")
    var dep: String,
    @SerializedName("id")
    var id: String

    )