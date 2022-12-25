package com.example.share2connect.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException


class ApiClient {
    private lateinit var apiService: ApiService

    fun getApiService(): ApiService {

        var tlsTocketFactory =  TLSSocketFactory();
       var client = tlsTocketFactory.getTrustManager()?.let {
           OkHttpClient.Builder()
               .sslSocketFactory(tlsTocketFactory, it)
           .build()
       }





      //  if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
      //  }

        return apiService
    }


}