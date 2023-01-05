package com.example.share2connect.Models

import com.google.gson.annotations.SerializedName

data class SignupReq (

    @SerializedName("userNameText")
    var userNameText: String,


    @SerializedName("userMail")
    var userMail: String,
    @SerializedName("userGender")
    var userGender: String,

    @SerializedName("userPassword")
    var userPassword: String,

    @SerializedName("userDepartment")
    var userDepartment: String,

    @SerializedName("userBio")
    var userBio: String,

    @SerializedName("userImageUrl")
    var userImage: String?=null,

    @SerializedName("userPhoneNumber")
    var userPhoneNumber: String,


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignupReq

        if (userNameText != other.userNameText) return false
        if (userMail != other.userMail) return false
        if (userGender != other.userGender) return false
        if (userPassword != other.userPassword) return false
        if (userDepartment != other.userDepartment) return false
        if (userBio != other.userBio) return false
        if (userImage != null) {
            if (other.userImage == null) return false
            if (!userImage.contentEquals(other.userImage)) return false
        } else if (other.userImage != null) return false
        if (userPhoneNumber != other.userPhoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userNameText.hashCode()
        result = 31 * result + userMail.hashCode()
        result = 31 * result + userGender.hashCode()
        result = 31 * result + userPassword.hashCode()
        result = 31 * result + userDepartment.hashCode()
        result = 31 * result + userBio.hashCode()
        result = 31 * result + userPhoneNumber.hashCode()
        return result
    }
}