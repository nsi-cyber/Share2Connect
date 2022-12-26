package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseModel (

    @SerializedName("announcements")
    var announcements: List<BaseComponent>

)