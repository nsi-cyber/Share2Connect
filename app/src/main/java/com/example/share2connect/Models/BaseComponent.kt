package com.example.share2connect.Models

import com.example.share2connect.Utils.AdvertEnum
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseComponent : Serializable {


    @SerializedName("category")
    val category: AdvertEnum? = null

    @SerializedName("user_id")
    val userId: Int? = null

    @SerializedName("post_id")
    val postId: Int? = null

    @SerializedName("data")
    val data: List<Any>? = null

}