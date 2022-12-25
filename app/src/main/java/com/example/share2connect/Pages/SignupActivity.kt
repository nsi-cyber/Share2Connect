package com.example.share2connect.Pages

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.share2connect.Models.SignupReq
import com.example.share2connect.Models.SignupResponse
import com.example.share2connect.R
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    companion object {
        val IMAGE_REQUEST_CODE = 1_000
    }

    var genderText = ""
    lateinit var radioGroup: RadioGroup
    lateinit var changePhoto: CardView
    lateinit var passConfCard: MaterialCardView
    lateinit var passCard: MaterialCardView
    lateinit var mailCard: MaterialCardView
    lateinit var signupButton: TextView
    lateinit var editBio: EditText
    lateinit var editFaculty: EditText
    lateinit var editPassConf: EditText
    lateinit var editPass: EditText
    lateinit var editMail: EditText
    lateinit var editName: EditText
    lateinit var photo: ImageView

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        ProviderInstaller.installIfNeeded(this);

        photo = findViewById<ImageView>(R.id.picture)
        editMail = findViewById<EditText>(R.id.editMail)
        editName = findViewById<EditText>(R.id.editName)
        editPass = findViewById<EditText>(R.id.editPass)
        editFaculty = findViewById<EditText>(R.id.editFaculty)
        editBio = findViewById<EditText>(R.id.editBio)
        editPassConf = findViewById<EditText>(R.id.editPassConf)
        signupButton = findViewById<TextView>(R.id.buttonSignup)

        changePhoto = findViewById<CardView>(R.id.changeCard)
        radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        apiClient = ApiClient(this)
        sessionManager = SessionManager(this)

        changePhoto.setOnClickListener {
            pickImageFromGallery()
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            var radioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            genderText = radioButton.text.toString()
        })
        var intent = Intent(this, LoginActivity::class.java)

        signupButton.setOnClickListener {
            //if api connected
         if (1==1) {

             if (checkEmpty()) {

                 apiClient.getApiService().singup(
                     SignupReq(email = editMail.text.toString(),
                         password = editPass.text.toString(),
                         gender = genderText,
                         fullName = editName.text.toString(),
                         department = editFaculty.text.toString())
                 )
                     .enqueue(object : Callback<SignupResponse> {

                         override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                    println(t.toString())
                         }

                         override fun onResponse(
                             call: Call<SignupResponse>,
                             response: Response<SignupResponse>
                         ) {
                             val signupResponse = response.body()

                             if (signupResponse?.status == 200) {
                                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                                 startActivity(intent)
                                 finish()
                             } else {
                                 // Error logging in
                             }
                         }
                     })

             } else
                 Toast.makeText(this, "Please fill all spaces correctly !", Toast.LENGTH_SHORT)
                     .show()
         } else
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

            startActivity(intent)
            finish()





        }


    }


    private fun checkEmpty(): Boolean {
        return (editPass.text.toString().equals(editPassConf.text.toString()))
                && (editName.text.length > 1)
                && (editPassConf.text.length > 1)
                && (editPass.text.length > 1)
                && (editMail.text.length > 1)
                && (genderText.length > 1)
                && (editBio.text.length > 1)
                && (editFaculty.text.length > 1)

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            photo.setImageURI(data?.data)
        }
    }
}