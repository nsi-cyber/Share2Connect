package com.example.share2connect.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.share2connect.Models.UserModel
import com.example.share2connect.Pages.ProfileEditFragment
import com.example.share2connect.R
import com.example.share2connect.Utils.Helper
import com.example.share2connect.retrofit.SessionManager
import com.google.firebase.storage.FirebaseStorage


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment(var user:UserModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit  var sessionManager : SessionManager




    lateinit var userImage: ImageView
    lateinit var userName: TextView
    lateinit var editProfile: TextView
    lateinit var userBio: TextView
    lateinit var userDepartment: TextView
    lateinit var userMail: TextView
    lateinit var userPhone: TextView
    lateinit var userAttends: LinearLayout
    lateinit var userAdverts: LinearLayout
    lateinit var whatsapp: CardView
    lateinit var gmail: CardView





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
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_user_profile, container, false)

        userImage = view.findViewById(R.id.userImage)
        userName = view.findViewById(R.id.nameText)
        userBio = view.findViewById(R.id.bioText)
        userDepartment = view.findViewById(R.id.departmentText)
        userMail = view.findViewById(R.id.mailText)
        userPhone = view.findViewById(R.id.phoneText)
        userAttends = view.findViewById(R.id.attends)
        userAdverts = view.findViewById(R.id.adverts)
        gmail = view.findViewById(R.id.mailCard)
        whatsapp = view.findViewById(R.id.whatsappCard)



        if(1==1) {//api
            userName.text = user.fullName
            userBio.text = user.about
            userDepartment.text = user.department
            userMail.text = user.email
            userPhone.text = user.phone


            /*
            val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(user!!.userImage!!)
            imageRef.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                userImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                // Handle any errors
            }
        */


        }

        userAdverts.setOnClickListener { activity?.let { it1 -> Helper.changeFragment(UserAdsFragment(user.id,user.fullName),


            it1.supportFragmentManager) } }
        userAttends.setOnClickListener { activity?.let { it1 -> Helper.changeFragment(JoinedFragment(
         1  // userObject.id
        ), it1.supportFragmentManager) } }



        whatsapp.setOnClickListener { user.phone?.let { it1 -> openWhatsApp(it1) } }
        gmail.setOnClickListener { user.email?.let { it1 -> openMail(it1) } }



        return view
    }

    fun openMail(mail:String){

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Campus uygulamasındaki ilan")

        startActivity(Intent.createChooser(intent, "Email via..."))

    }


    private fun openWhatsApp(smsNumber: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Merhaba, bir ilan için yazmıştım "
        )
        sendIntent.putExtra("jid", "$smsNumber@s.whatsapp.net") //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp")
        if (activity?.let { sendIntent.resolveActivity(it.getPackageManager()) } == null) {
            Toast.makeText(this.context, "Error/n", Toast.LENGTH_SHORT).show()
            return
        }
        startActivity(sendIntent)
    }



}