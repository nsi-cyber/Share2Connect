package com.example.share2connect.Models

import com.example.share2connect.Utils.AdvertEnum
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseComponent (


    @SerializedName("category")
    var category: String? = null,

    @SerializedName("user_id")
    val userId: Int? = 0,

    @SerializedName("post_id")
    val postId: Int? = 0,

    @SerializedName("data")
    var data: AdvertDataModel? = null

)