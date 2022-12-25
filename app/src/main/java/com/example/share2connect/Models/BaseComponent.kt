package com.example.share2connect.Models

import com.example.share2connect.Utils.AdvertEnum
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseComponent {


    @SerializedName("category")
    val category: String? = null

    @SerializedName("user_id")
    val userId: Int? = null

    @SerializedName("post_id")
    val postId: Int? = null

    @SerializedName("data")
    var data: Map<*, *>? = null

}