package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseModel : Serializable {

    @SerializedName("announcements")
    val announcements: List<BaseComponent>? = null

}