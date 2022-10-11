package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.Models.LoginReq
import com.example.bitirmeprojesi.Models.LoginResponse
import com.example.bitirmeprojesi.Models.SignupReq
import com.example.bitirmeprojesi.Models.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(@Body request: LoginReq): Call<LoginResponse>


    @POST(Constants.SIGNUP_URL)
    @FormUrlEncoded
    fun singup(@Body request: SignupReq): Call<SignupResponse>

}