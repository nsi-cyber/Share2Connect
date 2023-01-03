package com.example.share2connect.Pages

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.share2connect.Fragments.MyAdsFragment
import com.example.share2connect.Models.MessageResponse
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileEditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private var imageUri: Uri? = null

    private var genderText = ""
    private var isShowing = true
    private var imageHas = false
    private lateinit var radioGroup: RadioGroup
    private lateinit var changePhoto: CardView
    private lateinit var save: Button
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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        with(view) {
            photo = findViewById(R.id.picture)
            passShow = findViewById(R.id.passShow)
            editName = findViewById(R.id.editName)
            editPass = findViewById(R.id.editPass)
            editFaculty = findViewById(R.id.editFaculty)
            editBio = findViewById(R.id.editBio)
            save = findViewById(R.id.buttonSave)
            editTextPhone = findViewById(R.id.editTextPhone)

            changePhoto = findViewById(R.id.changeCard)
            radioGroup = findViewById(R.id.radioGroup)
        }

        apiClient = ApiClient(requireContext())
        sessionManager = SessionManager(requireContext())

       var usr= sessionManager.getUserObject()
        editName.setText(usr!!.fullName)
        editFaculty.setText(usr.department)
        editBio.setText(usr.about)
        editTextPhone.setText(usr.phone)







        changePhoto.setOnClickListener {
            pickImageFromGallery()
        }

        passShow.setOnClickListener {
            if (!isShowing) {
                isShowing = false
                passShow.setImageResource(R.drawable.ic_hide)
                editPass.inputType = InputType.TYPE_CLASS_TEXT
                editPassConf.inputType = InputType.TYPE_CLASS_TEXT

            } else {
                isShowing = true
                passShow.setImageResource(R.drawable.ic_eye)
                editPass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                editPassConf.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

        }



        save.setOnClickListener {
            val name = editName.text.toString().trim()
            val mail = editMail.text.toString().trim()
            val pass = editPass.text.toString().trim()
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

            val mProgressDialog = ProgressDialog(requireContext())
            mProgressDialog.setTitle("Hesap Güncelleniyor")
            mProgressDialog.setMessage("Lütfen Bekleyiniz")
            mProgressDialog.show()


            usr.about=editBio.text.toString()
            usr.fullName=editName.text.toString()
            usr.department=editFaculty.text.toString()
            usr.phone=editTextPhone.text.toString()

            apiClient.getApiService().updateUser(usr)  .enqueue(object : Callback<MessageResponse> {

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    println(t.toString())
                }

                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    val postResponse = response.body()

                    if (postResponse?.status == 200) {
                        sessionManager.saveUserObject(usr)

                        Helper.changeFragment(MainFragment(), activity!!.supportFragmentManager)
                    } else {
                        // Error logging in
                    }
                }
            })




        }






            return view


    }



    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == 1000) {
            imageUri = data?.data
            imageHas = true
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
            photo.setImageBitmap(bitmap)
        }
    }




}