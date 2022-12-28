package com.example.share2connect.retrofit

import com.example.share2connect.Models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun login(@Body request: LoginReq): Call<LoginResponse>


    @POST(Constants.SIGNUP_URL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun singup(@Body request: SignupReq): Call<SignupResponse>

    @POST(Constants.NEW_POST)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun post(@Body request: BaseComponent): Call<AdvertResponse>


    @GET(Constants.GET_DATA_ALL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun getHomeData(): Call<AnnouncementsResponse>






    @GET("/api/announcements/user/{id}")
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun getUserPosts(@Path("id")  userId: Int): Call<AnnouncementsResponse>



    @GET("api/users/get-user/{id}")
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun getUser(@Path("id")  userId: Int): Call<LoginResponse>


}