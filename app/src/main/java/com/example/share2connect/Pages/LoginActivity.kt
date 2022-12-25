package com.example.share2connect.Pages

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.share2connect.MainActivity
import com.example.share2connect.Models.LoginReq
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.R
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.FileNameMap

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var layout = findViewById<ConstraintLayout>(R.id.layout)
        var editMail = findViewById<EditText>(R.id.editMail)
        var editPass = findViewById<EditText>(R.id.editPass)
        var loginButton = findViewById<Button>(R.id.buttonLogin)
        var signupButton = findViewById<TextView>(R.id.buttonSignup)


        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        val intentLogin = Intent(this, MainActivity::class.java)

        apiClient = ApiClient(this)
        sessionManager = SessionManager(this)
sessionManager.clearAll()
        loginButton.setOnClickListener {
            if(1==1){



            apiClient.getApiService()
            .login(LoginReq(userMail = editMail.text.toString(), userPassword = editPass.text.toString()))
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
                        intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                        //Todo save to sharedpreferences name
                        sessionManager.saveUserObject(response.body()!!.user)

                        startActivity(intentLogin)
                        finish()

                    } else {
                        // Error logging in
                    }
                }
            })

            }
                else{
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

            startActivity(intentLogin)
                finish()

        }



    }
}}
