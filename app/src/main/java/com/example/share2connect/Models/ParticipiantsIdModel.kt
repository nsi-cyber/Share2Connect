
package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class ParticipiantsIdModel (

    @SerializedName("status")
    var status: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("user_id")
    var userId: String



)