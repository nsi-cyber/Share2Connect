package com.example.share2connect.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException


class ApiClient(val context:Context) {
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService {

        if(SessionManager(context).fetchAuthToken().toString()!=null){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)


                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${SessionManager(context).fetchAuthToken()
                    }").build()
                    chain.proceed(request)
                }.build())



                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        else {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)


    }
        return apiService


}


}