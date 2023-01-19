package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AdvertDataModel (


    //@SerializedName("adCategory")
  //  var adCategory: String?=null,

  //  @SerializedName("publishDate")
  //  var publishDate: String?=null,

    @SerializedName("isAvailable")
    var isAvailable: Boolean?=null,

    @SerializedName("adNameText")
    var adNameText: String?=null,

    @SerializedName("adDescText")
    var adDescText: String?=null,

    @SerializedName("adImageUrl")
    var adImage: String?=null,

    @SerializedName("adClubName")
    var adClubName: String?=null,

    @SerializedName("adDateText")
    var adDateText: String?=null,

    @SerializedName("adTicketText")
    var adTicketText: String?=null,

    @SerializedName("adPriceText")
    var adPriceText: String?=null,

    @SerializedName("adSeatText")
    var adSeatText: String?=null,

    @SerializedName("adPlaceText")
    var adPlaceText: String?=null,
    @SerializedName("adPlaceGPS")
    var adPlaceGPS: String?=null,
    @SerializedName("adRouteStartText")
    var adRouteStartText: String?=null,
    @SerializedName("adRouteEndText")
    var adRouteEndText: String?=null,
    @SerializedName("adRouteStartGPS")
    var adRouteStartGPS: String?=null,
    @SerializedName("adRouteEndGPS")
    var adRouteEndGPS: String?=null,
    @SerializedName("participants")
    var participants: ArrayList<Int>?=null,

    )