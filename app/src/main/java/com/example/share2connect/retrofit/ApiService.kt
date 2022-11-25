package com.example.share2connect.retrofit

import com.example.share2connect.Models.LoginReq
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.Models.SignupReq
import com.example.share2connect.Models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.LOGIN_URL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun login(@Body request: LoginReq): Call<LoginResponse>


    @POST(Constants.SIGNUP_URL)
    @Headers("Accept: application/json",
        "Content-Type: application/json")
    fun singup(@Body request: SignupReq): Call<SignupResponse>

}