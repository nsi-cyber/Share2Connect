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
    fun post(@Body request: AdvertDataModel): Call<AdvertResponse>


    @GET(Constants.GET_DATA_ALL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun getHomeData(): Call<BaseModel>






    @GET(Constants.GET_POST)
    fun getPost(@Body postId: String): Call<Map<*,*>>


}