package com.example.share2connect.Pages

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.share2connect.Models.SignupReq
import com.example.share2connect.Models.SignupResponse
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper.Companion.imageToBitmap
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {


    private var imageUri: Uri? = null

    private var genderText = ""
    private var isShowing = true
    private var imageHas = false
    private lateinit var radioGroup: RadioGroup
    private lateinit var changePhoto: CardView
    private lateinit var signupButton: TextView
    private lateinit var editBio: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editFaculty: EditText
    private lateinit var editPassConf: EditText
    private lateinit var editPass: EditText
    private lateinit var editMail: EditText
    private lateinit var editName: EditText
    private lateinit var photo: ImageView
    private lateinit var passShow: ImageView
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        ProviderInstaller.installIfNeeded(this)

        photo = findViewById(R.id.picture)
        passShow = findViewById(R.id.passShow)
        editMail = findViewById(R.id.editMail)
        editName = findViewById(R.id.editName)
        editPass = findViewById(R.id.editPass)
        editFaculty = findViewById(R.id.editFaculty)
        editBio = findViewById(R.id.editBio)
        editPassConf = findViewById(R.id.editPassConf)
        signupButton = findViewById(R.id.buttonSignup)
        editTextPhone = findViewById(R.id.editTextPhone)

        changePhoto = findViewById(R.id.changeCard)
        radioGroup = findViewById(R.id.radioGroup)

        apiClient = ApiClient(this)
        sessionManager = SessionManager(this)

        changePhoto.setOnClickListener {
            pickImageFromGallery()
        }

        passShow.setOnClickListener {
            if(!isShowing)
            {
                isShowing=true
                passShow.setImageResource(R.drawable.ic_hide)
                editPass.inputType=InputType.TYPE_CLASS_TEXT
                editPassConf.inputType=InputType.TYPE_CLASS_TEXT

            }
            else {
                isShowing=false
                passShow.setImageResource(R.drawable.ic_eye)
                editPass.inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD
                editPassConf.inputType= InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

        }

        radioGroup.setOnCheckedChangeListener { _, i ->
            val radioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            genderText = radioButton.text.toString()
            if (!imageHas) {
                when (genderText) {
                    "Erkek" -> photo.setImageResource(R.drawable.ic_male)
                    "Kadın" -> photo.setImageResource(R.drawable.ic_female)
                    "Cevaplamıyorum" -> photo.setImageResource(R.drawable.ic_cat)
                }
            }
        }
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).flags = Intent.FLAG_ACTIVITY_NO_HISTORY

        signupButton.setOnClickListener {
            val name = editName.text.toString().trim()
            val mail = editMail.text.toString().trim()
            val pass = editPass.text.toString().trim()
            val passConf = editPassConf.text.toString().trim()
            val faculty = editFaculty.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val bio = editBio.text.toString().trim()

            if (name.isEmpty()) {
                editName.error = "Bu alan boş bırakılamaz"
                editName.requestFocus()
                return@setOnClickListener
            }

            if (mail.isEmpty()) {
                editMail.error = "Bu alan boş bırakılamaz"
                editMail.requestFocus()
                return@setOnClickListener
            }

            if (pass.isEmpty()) {
                editPass.error = "Bu alan boş bırakılamaz"
                editPass.requestFocus()
                return@setOnClickListener
            }

            if (passConf.isEmpty()) {
                editPassConf.error = "Bu alan boş bırakılamaz"
                editPassConf.requestFocus()
                return@setOnClickListener
            }

            if (faculty.isEmpty()) {
                editFaculty.error = "Bu alan boş bırakılamaz"
                editFaculty.requestFocus()
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                editTextPhone.error = "Bu alan boş bırakılamaz"
                editTextPhone.requestFocus()
                return@setOnClickListener
            }

            if (bio.isEmpty()) {
                editBio.error = "Bu alan boş bırakılamaz"
                editBio.requestFocus()
                return@setOnClickListener
            }

            if (pass != passConf) {
                editPassConf.error = "Şifreler uyuşmuyor"
                editPassConf.requestFocus()
                return@setOnClickListener
            }
            val mProgressDialog = ProgressDialog(this)
            mProgressDialog.setTitle("Kayıt Olunuyor")
            mProgressDialog.setMessage("Lütfen Bekleyiniz")
            mProgressDialog.show()
            val signupReq = SignupReq(userNameText = name, userMail =  mail, userPassword = pass, userDepartment = faculty, userPhoneNumber = phone, userBio = bio, userImage =  imageToBitmap(photo), userGender = genderText)
            val call = apiClient.getApiService().singup(signupReq)
            call.enqueue(object : Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if (response.isSuccessful) {

                        val signupResponse = response.body()
                        println("signup Response: " + signupResponse.toString())
                        if (response.code() == 200) {
                            Toast.makeText(this@SignupActivity, "Kayıt Başarılı", Toast.LENGTH_LONG)
                                .show()

                            startActivity(intent)
                            mProgressDialog.hide()

                            finish()
                        }
                        mProgressDialog.hide()



                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_LONG).show()
                    mProgressDialog.hide()

                }
            })
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
            imageHas = true
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            photo.setImageBitmap(bitmap)
        }
    }
}
