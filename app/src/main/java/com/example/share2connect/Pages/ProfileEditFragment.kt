package com.example.share2connect.Pages

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
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
import com.example.share2connect.Models.UpdateUserResponse
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.ApiClient
import com.example.share2connect.retrofit.SessionManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

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
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    val storage = Firebase.storage("gs://share2connect-93ec8.appspot.com")



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
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        with(view) {
            photo = findViewById(R.id.picture)
            editName = findViewById(R.id.editName)
            editPass = findViewById(R.id.editPass)
            editFaculty = findViewById(R.id.editFaculty)
            editBio = findViewById(R.id.editBio)
            save = findViewById(R.id.buttonSave)
            editTextPhone = findViewById(R.id.editTextPhone)

            changePhoto = findViewById(R.id.changeCard)
        }

        val uris=SessionManager(requireContext()).getUserObject()?.userImage!!.toString()
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uris)
        imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            photo.setImageBitmap(bitmap)
        }.addOnFailureListener {
            // Handle any errors
        }

        apiClient = ApiClient(requireContext())
        sessionManager = SessionManager(requireContext())

       var usr= sessionManager.getUserObject()
        editName.setText(usr!!.fullName)
        editFaculty.setText(usr.department)
        editBio.setText(usr.about)
        editTextPhone.setText(usr.phone)







        changePhoto.setOnClickListener {
            launchGallery()
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
            var paths=uploadImage()

usr.userImage="gs://share2connect-93ec8.appspot.com/"+paths

            apiClient.getApiService().updateUser(usr)  .enqueue(object : Callback<UpdateUserResponse> {

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    println(t.toString())
                }

                override fun onResponse(
                    call: Call<UpdateUserResponse>,
                    response: Response<UpdateUserResponse>
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


    private fun uploadImage():String{
        var str="myImages/" + UUID.randomUUID().toString()
        if(filePath != null){
            val ref = storageReference?.child(str)
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(requireContext(), "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
        return str
    }
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 12312)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12312 && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                photo.setImageBitmap(bitmap)
                imageHas=true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }



}