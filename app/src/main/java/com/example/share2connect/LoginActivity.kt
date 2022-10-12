package com.example.share2connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.share2connect.Models.LoginReq
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var layout = findViewById<ConstraintLayout>(R.id.layout)
        var editMail = findViewById<EditText>(R.id.editMail)
        var editPass = findViewById<EditText>(R.id.editPass)
        var loginButton = findViewById<Button>(R.id.buttonLogin)
        var signupButton = findViewById<TextView>(R.id.buttonSignup)
        var mailCard = findViewById<MaterialCardView>(R.id.mailCard)
        var passCard = findViewById<MaterialCardView>(R.id.passCard)

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        val intentLogin = Intent(this, MainActivity::class.java)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        loginButton.setOnClickListener {
            apiClient.getApiService()
            .login(LoginReq(email = editMail.text.toString(), password = editPass.text.toString()))
            .enqueue(object : Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    println("error= "+ t )
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse?.status == 200 && loginResponse.user != null) {
                        sessionManager.saveAuthToken(loginResponse.token)
                        startActivity(intentLogin)

                    } else {
                        // Error logging in
                    }
                }
            })}


    }
}
