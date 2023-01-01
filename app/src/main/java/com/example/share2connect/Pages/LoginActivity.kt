package com.example.share2connect.Pages

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.share2connect.MainActivity
import com.example.share2connect.Models.LoginReq
import com.example.share2connect.Models.LoginResponse
import com.example.share2connect.R
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private var isShowing = false

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this)
        sessionManager.clearAll()
        var pastUserMail = sessionManager.getUserMail()



        var editMail = findViewById<EditText>(R.id.editMail)
        var rememberMe = findViewById<CheckBox>(R.id.rememberMe)
        var editPass = findViewById<EditText>(R.id.editPass)
        var loginButton = findViewById<Button>(R.id.buttonLogin)
        var signupButton = findViewById<TextView>(R.id.buttonSignup)
        var passShow = findViewById<ImageView>(R.id.passShow)

if(pastUserMail!=null)
    editMail.setText(pastUserMail, TextView.BufferType.EDITABLE);




        passShow.setOnClickListener {
            if (!isShowing) {
                isShowing = true
                passShow.setImageResource(R.drawable.ic_hide)
                editPass.inputType = InputType.TYPE_CLASS_TEXT

            } else {
                isShowing = false
                passShow.setImageResource(R.drawable.ic_eye)
                editPass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD


            }

        }


        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        val intentLogin = Intent(this, MainActivity::class.java)

        apiClient = ApiClient(this)
        loginButton.setOnClickListener {

            val email = editMail.text.toString()
            val password = editPass.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter a valid email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val mProgressDialog = ProgressDialog(this)
            mProgressDialog.setTitle("Giriş Yapılıyor")
            mProgressDialog.setMessage("Lütfen Bekleyiniz")
            mProgressDialog.show()

            apiClient.getApiService().login(LoginReq(userMail = email, userPassword = password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println("error= " + t)
                        mProgressDialog.hide()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val loginResponse = response.body()

                        if (loginResponse?.status == 200 && loginResponse.user != null) {
                            loginResponse.token?.let { sessionManager.saveAuthToken(it) }
                            intentLogin.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY
                            sessionManager.saveUserObject(response.body()!!.user)
                            startActivity(intentLogin)

                            if(rememberMe.isChecked)
                                sessionManager.saveUserMail(editMail.text.toString())
                            mProgressDialog.hide()
                            finish()
                        } else {
                            mProgressDialog.hide()
                            println(response.message() + "error")
                        }
                    }
                })
        }

    }


}
