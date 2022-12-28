package com.example.share2connect.Pages

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import java.io.ByteArrayOutputStream

class SignupActivity : AppCompatActivity() {
    companion object {
        val IMAGE_REQUEST_CODE = 1_000
    }
    private var imageUri: Uri? = null

    var genderText = ""
    var imageHas=false
    lateinit var radioGroup: RadioGroup
    lateinit var changePhoto: CardView
    lateinit var passConfCard: MaterialCardView
    lateinit var passCard: MaterialCardView
    lateinit var mailCard: MaterialCardView
    lateinit var signupButton: TextView
    lateinit var editBio: EditText
    lateinit var editTextPhone: EditText
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
        editTextPhone = findViewById<EditText>(R.id.editTextPhone)

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
if(imageHas){
    apiClient.getApiService().singup(
    SignupReq(userMail = editMail.text.toString(),
        userPassword = editPass.text.toString(),
        userGender = genderText,
        userNameText = editName.text.toString(),
        userDepartment = editFaculty.text.toString(),
        userPhoneNumber = editTextPhone.text.toString(),
        userImage = imageToBitmap(photo),
        userBio = editBio.text.toString()
    )
)}
                 else{      apiClient.getApiService().singup(
    SignupReq(userMail = editMail.text.toString(),
        userPassword = editPass.text.toString(),
        userGender = genderText,
        userNameText = editName.text.toString(),
        userDepartment = editFaculty.text.toString(),
        userPhoneNumber = editTextPhone.text.toString(),
        userBio = editBio.text.toString()
    )
)}


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

    private fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
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
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100){
            imageUri = data?.data
            photo.setImageURI(imageUri)
        }
    }}
