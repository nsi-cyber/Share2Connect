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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var userObject: UserModel
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
        sessionManager = SessionManager(this.requireContext())

        if(1==0)
            userObject=sessionManager.getUserObject()!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_user_profile, container, false)

        userImage = view.findViewById(R.id.userImage)
        userName = view.findViewById(R.id.userName)
        userBio = view.findViewById(R.id.bioText)
        userDepartment = view.findViewById(R.id.userDepartment)
        userMail = view.findViewById(R.id.mailText)
        userPhone = view.findViewById(R.id.phoneText)
        userAttends = view.findViewById(R.id.attends)
        userAdverts = view.findViewById(R.id.adverts)
        gmail = view.findViewById(R.id.mailCard)
        whatsapp = view.findViewById(R.id.whatsappCard)



        if(1==0) {//api
            userName.text = userObject.FullName
            userBio.text = userObject.About
            userDepartment.text = userObject.Department
            userMail.text = userObject.Email
            userPhone.text = userObject.Phone
            val bmp = BitmapFactory.decodeByteArray(userObject.Image, 0, userObject.Image.size)
            userImage.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp,
                    userImage.width,
                    userImage.height,
                    false
                )
            )
        }

        userAdverts.setOnClickListener { activity?.let { it1 -> Helper.changeFragment(UserAdsFragment(userObject.id), it1.supportFragmentManager) } }
        userAttends.setOnClickListener { activity?.let { it1 -> Helper.changeFragment(JoinedFragment(
         1  // userObject.id
        ), it1.supportFragmentManager) } }



        whatsapp.setOnClickListener { openWhatsApp(userObject.Phone) }
        gmail.setOnClickListener { openMail(userObject.Email) }



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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}