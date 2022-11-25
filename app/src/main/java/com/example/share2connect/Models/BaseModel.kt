package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseModel : Serializable {

    @SerializedName("components")
    val components: List<BaseComponent>? = null

}